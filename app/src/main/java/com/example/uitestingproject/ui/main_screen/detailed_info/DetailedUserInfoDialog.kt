package com.example.uitestingproject.ui.main_screen.detailed_info

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.uitestingproject.UiTestingApplication
import com.example.uitestingproject.databinding.DialogDitailedInfoBinding
import javax.inject.Inject

class DetailedUserInfoDialog : DialogFragment() {

    private lateinit var binding: DialogDitailedInfoBinding

    @Inject
    protected lateinit var viewModel: DetailedUserInfoViewModel

    override fun onAttach(context: Context) {
        (context.applicationContext as UiTestingApplication).applicationGraph.inject(this)
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            binding = DialogDitailedInfoBinding.inflate(it.layoutInflater)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.updateUser(arguments?.getString(ARG_USER_NAME))

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            ).collect {
                when (it) {
                    is DetailedUserInfoViewModel.State.Success -> binding.user = it.user
                    else -> dismiss()
                }
            }
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnGoToGitHub.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(viewModel.getUserUri() ?: return@setOnClickListener)
            activity?.startActivity(openURL)
        }

        return binding.root
    }

    companion object {
        const val ARG_USER_NAME = "arg_username"
    }
}