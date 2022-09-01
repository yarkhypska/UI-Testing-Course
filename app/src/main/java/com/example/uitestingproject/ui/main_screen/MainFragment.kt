package com.example.uitestingproject.ui.main_screen

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.uitestingproject.R
import com.example.uitestingproject.UiTestingApplication
import com.example.uitestingproject.data.user.local.UserInListEntity
import com.example.uitestingproject.databinding.FragmentMainBinding
import com.example.uitestingproject.ui.NetworkConnectionStateHolder
import com.example.uitestingproject.ui.main_screen.MainFragmentDirections.Companion.actionMainFragmentToDetailedUserInfoDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    protected lateinit var viewModel: MainScreenViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private var _adapter: UserAdapter? = null
    private val adapter: UserAdapter
        get() = _adapter!!

    private val mDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        (context.applicationContext as UiTestingApplication).applicationGraph.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //should be first to init adapter, used further in initialization.
        initRecyclerView()
        initPager()
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    binding.state = it
                }
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun initPager() {
        viewModel.pageSourceFlow
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.submitData(lifecycle, it)
            }.also {
                mDisposable.add(it)
            }
    }

    private fun initRecyclerView() {
        _adapter = UserAdapter(UserAdapter.UserComparator) { user ->
            onItemClick(user)
        }
        binding.rv.adapter = adapter
    }

    private fun onItemClick(user: UserInListEntity) {
        user.login?.let {
            if (NetworkConnectionStateHolder.connectionState.value) {
                findNavController().navigate(
                    actionMainFragmentToDetailedUserInfoDialog(it)
                )
            } else {
                AlertDialog.Builder(context)
                    .setTitle(R.string.fragment_main_dialog_alert_offline_title)
                    .setMessage(R.string.fragment_main_dialog_alert_offline_message)
                    .setNegativeButton(R.string.fragment_main_dialog_alert_offline_btn_dismiss) { dialog, _ ->
                        dialog.dismiss()
                    }.create()
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
        mDisposable.clear()
    }
}