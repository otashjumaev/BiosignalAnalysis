package org.hiro.biosignals.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hiro.biosignals.R
import org.hiro.biosignals.database.MyDatabase
import org.hiro.biosignals.databinding.DataSetFragmentBinding
import org.hiro.biosignals.databinding.ItemDataSetBinding
import org.hiro.biosignals.model.DataSet
import org.hiro.biosignals.util.DatasetDialog
import org.hiro.biosignals.util.formatTime
import org.hiro.biosignals.viewmodel.DataSetViewModel
import org.hiro.biosignals.viewmodel.factory.DataSetViewModelFactory
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DataSetFragment : BaseFragment<DataSetFragmentBinding>(DataSetFragmentBinding::inflate) {

    private lateinit var viewModel: DataSetViewModel
    private lateinit var getContent: ActivityResultLauncher<String>
    private lateinit var adapter: ListDelegationAdapter<List<DataSet>>
    private lateinit var dataList: List<DataSet>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = MyDatabase.getInstance(requireContext())
        val userId = arguments?.getLong("USER_ID")!!
        viewModel = ViewModelProvider(this, DataSetViewModelFactory(db.datasetDao, userId))
            .get(DataSetViewModel::class.java)
        initContentPicker()
        intiListeners()
        initList()
    }

    private fun initList() {
        adapter = ListDelegationAdapter(viewModel.dataSetAdapterDelegate {
            val b = Bundle()
            b.putString("FILE_PATH", it.filePath)
            findNavController().navigate(R.id.fragmentMain, b)
        })
        dataList = viewModel.dataList.value ?: mutableListOf()
        adapter.items = dataList
        if (!dataList.isNullOrEmpty())
            binding.emptyListText.visibility = View.GONE
        binding.dataList.layoutManager = LinearLayoutManager(requireContext())
        binding.dataList.adapter = adapter
    }

    private fun intiListeners() {
        binding.fabDataSet.setOnClickListener {
            getContent.launch("text/plain")
        }

        binding.showAvgBtn.setOnClickListener {
            val b = Bundle()
            b.putInt("FILE_COUNT", dataList.size)
            dataList.forEachIndexed { index, dataSet ->
                b.putString("FILE_PATH$index", dataSet.filePath)
            }
            findNavController().navigate(R.id.avgStatFragment, b)
        }

        viewModel.dataList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.emptyListText.visibility = View.GONE
                dataList = it
                adapter.items = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private fun saveData(uri: Uri) {
        val cntResolver = requireContext().contentResolver
        val path: File = requireContext().filesDir
        lifecycleScope.launch {
            val fileName = suspendCoroutine<String> { cor ->
                DatasetDialog(requireContext()) {
                    cor.resume(it)
                }
            }
            val file = File(path, fileName)
            withContext(Dispatchers.IO) {
                cntResolver.openInputStream(uri)?.bufferedReader()?.copyTo(file.writer())
            }
            val size = file.readLines().size
            if (size == 0)
                Snackbar.make(binding.root, "Wrong Input Data", Snackbar.LENGTH_SHORT).show()
            else {
                Log.d("TAG3", "FG save: ${file.name} $size")
                viewModel.saveDataSet(file.name, size, file.absolutePath)
                if (dataList.isNotEmpty())
                    binding.emptyListText.visibility = View.GONE
//            adapter.notifyItemInserted(dataList.size - 1)
            }
        }
    }


    private fun initContentPicker() {
        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                try {
                    saveData(it)
                } catch (e: IOException) {
                    Toast.makeText(
                        requireContext(),
                        "Error occured while reading from file",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}