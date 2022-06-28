package com.emmanuelamet.retrofit_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.emmanuelamet.retrofit_mvvm.adapter.TodoAdapter
import com.emmanuelamet.retrofit_mvvm.api.RetrofitInstance
import com.emmanuelamet.retrofit_mvvm.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException
const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerview()
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try{
                RetrofitInstance.api.getTodos()
            }catch(e: IOException){
                binding.progressBar.isVisible = false
                Log.d(TAG, "IOException Error occurred")
                return@launchWhenCreated
            }
            catch (e: HttpException){
                binding.progressBar.isVisible = true
                Log.d(TAG, "HttpException Error occurred")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null){
                todoAdapter.todos = response.body()!!
            }else{
                Log.d(TAG, "Response not successful")
            }
            binding.progressBar.isVisible = false
        }
    }

    fun setupRecyclerview() = binding.rvTodo.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}