package com.example.goodlook

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchBottomScreen : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_bottom_screen, container, false)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new BottomSheetDialog with the specified theme.
        val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)

        // Set an OnShowListener for the dialog to perform custom actions when shown.
        dialog.setOnShowListener { dialogInterface ->
            // Cast the dialog to a BottomSheetDialog to access its features.
            val bottomSheetDialog = dialogInterface as BottomSheetDialog

            // Find the root layout of the BottomSheetDialog.
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            // Make sure the layout is not null before proceeding.
            parentLayout?.let {
                // Get the BottomSheetBehavior associated with the layout.
                val behavior = BottomSheetBehavior.from(it)

                // Set the layout's height to MATCH_PARENT to take up the full screen height.
                setupFullHeight(it)

                // Set the state of the BottomSheetBehavior to STATE_EXPANDED to fully show the dialog.
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        // Return the customized BottomSheetDialog.
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        // Get the layout params of the view.
        val layoutParams = bottomSheet.layoutParams

        // Set the height to MATCH_PARENT to make the view take up the full height of the screen.
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT

        // Apply the updated layout params to the view.
        bottomSheet.layoutParams = layoutParams
    }

}
