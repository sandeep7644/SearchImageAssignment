package com.example.assignment.features.add_comment

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout.VERTICAL
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.models.ImageItem
import com.example.assignment.utils.BindingAdapters
import com.example.assignment.utils.setToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_comment.*
import kotlinx.android.synthetic.main.add_comment_field_layout.*
import kotlinx.android.synthetic.main.toolbar_home.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddCommentActivity : AppCompatActivity() {
    val commentViewModel by viewModels<AddCommentViewModel>()
    val commentListRecyclerviewAdapter = CommentListRecyclerviewAdapter()
    var imageId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)
        val imageItem = intent.getParcelableExtra<ImageItem>("EXTRA_IMAGE")
        imageId = imageItem?.id.toString()
        BindingAdapters.setImageByUrl(imageView, imageItem?.link)
        recyclerView.adapter = commentListRecyclerviewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        lifecycleScope.launch {
            commentViewModel.getComments(imageId).collectLatest {
                commentListRecyclerviewAdapter.submitData(it)
            }
        }

        imageItem?.title?.let { setUpToolbar(it) }
        enter_comment.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                add_comment.isEnabled = s?.length!! > 0
            }
        })

        add_comment.setOnClickListener {
            if (enter_comment.text.toString().isNotEmpty()) {
                commentViewModel.addComment(imageId, enter_comment.text.toString())
                enter_comment.text.clear()
            }


        }
    }

    companion object {

        private const val EXTRA_IMAGE = "EXTRA_IMAGE"

        fun startActivity(context: Context, imageItem: ImageItem) {
            if (context is Activity) {
                val intent = Intent(context, AddCommentActivity::class.java)
                intent.putExtra(EXTRA_IMAGE, imageItem)
                context.startActivity(intent)
            }
        }
    }

    private fun setUpToolbar(title: String) {
        setToolbar(toolbar, title)

    }
}