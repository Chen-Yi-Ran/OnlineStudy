package com.example.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.LogUtils
import com.example.study.adapter.adapter.StudyPagerAdapter
import com.example.study.ui.StudyViewModel
import com.google.android.material.tabs.TabLayout



class StudyFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(StudyViewModel::class.java) }
//    private val repoAdapter = RepoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view = LayoutInflater.from(context).inflate(R.layout.fragment_study_main, container, false)
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
//        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = repoAdapter.withLoadStateFooter(FooterAdapter { repoAdapter.retry() })
//        lifecycleScope.launch {
//            viewModel.getPagingData().collect { pagingData ->
//                repoAdapter.submitData(pagingData)
//            }
        studyPagerAdapter = StudyPagerAdapter(childFragmentManager)
        viewModel.getCategory(studyPagerAdapter)
        val mTableLayout = view?.findViewById<TabLayout>(R.id.tab_layout)
        val mViewPager= view?.findViewById<ViewPager>(R.id.view_pager)
        mTableLayout?.setupWithViewPager(mViewPager)
        mViewPager?.adapter=studyPagerAdapter
      //  LogUtils.d("mTableLayout${mTableLayout}mViewPager${mViewPager}")
        return view
        }
    lateinit var studyPagerAdapter:StudyPagerAdapter



//        repoAdapter.addLoadStateListener {
//            when (it.refresh) {
//                is LoadState.NotLoading -> {
//                    progressBar.visibility = View.INVISIBLE
//                    recyclerView.visibility = View.VISIBLE
//                }
//                is LoadState.Loading -> {
//                    progressBar.visibility = View.VISIBLE
//                    recyclerView.visibility = View.INVISIBLE
//                }
//                is LoadState.Error -> {
//                    val state = it.refresh as LoadState.Error
//                    progressBar.visibility = View.INVISIBLE
//                    Toast.makeText(context, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

    }



