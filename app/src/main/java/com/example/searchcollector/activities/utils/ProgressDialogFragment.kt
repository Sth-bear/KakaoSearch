package com.example.searchcollector.activities.utils

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ProgressDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return ProgressDialog(requireContext()).apply {
            setMessage("로딩 중...")
            isIndeterminate = true // 무한진행 여부 설정
            setCancelable(false) // 취소 가능 여부 설정
        }
    }
}