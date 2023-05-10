package com.example.study.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.example.service.model.CategoryProject
import com.example.study.R
import com.example.study.adapter.adapter.FooterAdapter
import com.example.study.adapter.adapter.RepoAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StudyPageFragment : Fragment(),RepoAdapter.ItemCallback {


    companion object {
        fun newInstance(category: CategoryProject.Data): StudyPageFragment {
            val studyPageFragment = StudyPageFragment()
            val bundle = Bundle()
            bundle.putString("KEY_CATEGORYPROJECT_NAME", category.name)
            bundle.putInt("KEY_CATEGORYPROJECT_ID", category.id)
            studyPageFragment.arguments = bundle
            return studyPageFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_study, container, false)
        initData(view)
        return view
    }

    private val repoAdapter = RepoAdapter()
    private val viewModel by lazy { ViewModelProvider(this).get(StudyViewModel::class.java) }
    private fun initData(view:View) {
        val arguments = this.arguments
        val name = arguments?.getString("KEY_CATEGORYPROJECT_NAME")
        val id = arguments?.getInt("KEY_CATEGORYPROJECT_ID")
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        repoAdapter.setOnItemClickListener(this)
        LogUtils.d(recyclerView)
        recyclerView?.adapter =
            repoAdapter.withLoadStateFooter(FooterAdapter { repoAdapter.retry() })
        LogUtils.d(id)
        lifecycleScope.launch {
            viewModel.getPagingData(id!!).collect { pagingData ->
                repoAdapter.submitData(pagingData)
            }
        }
        repoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    progressBar?.visibility = View.INVISIBLE
                    recyclerView?.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    progressBar?.visibility = View.VISIBLE
                    recyclerView?.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    progressBar?.visibility = View.INVISIBLE
                    Toast.makeText(context, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

}

    override fun toWebViewActivity(url: String, id: Int, title: String) {
        Intent(activity, WebViewActivity::class.java).run {
            putExtra("CONTENT_URL_KEY", url)
            putExtra("CONTENT_ID_KEY", id)
            putExtra("CONTENT_TITLE_KEY", title)
            startActivity(this)
        }
    }


}