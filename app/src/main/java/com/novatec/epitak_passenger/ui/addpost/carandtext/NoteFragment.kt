package com.novatec.epitak_passenger.ui.addpost.carandtext

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.ui.addpost.AddPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note.*
import splitties.experimental.ExperimentalSplittiesApi


@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.fragment_note) {

    val args: NoteFragmentArgs by navArgs()
    private val actViewModel: AddPostViewModel by activityViewModels()
    private val viewModel: NoteViewModel by viewModels()
    lateinit var navController: NavController

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (args.ISFROMPOSTPREVIEW) {
//            noteInput.setText(actViewModel.note)
//        }

        attachListeners()
        subscribeObservers()

        navController = findNavController()
    }


    @ExperimentalSplittiesApi
    private fun attachListeners() {
        navNext.setOnClickListener {
//            actViewModel.note =
//                if (!noteInput.text.isNullOrBlank()) noteInput.text.toString() else ""
//
//            navController.navigate(if (args.ISFROMPOSTPREVIEW) R.id.action_carAndTextFragment_to_previewFragment else R.id.action_carAndTextFragment_to_previewFragment)
        }
    }

    @ExperimentalSplittiesApi
    private fun subscribeObservers() {

    }


}




