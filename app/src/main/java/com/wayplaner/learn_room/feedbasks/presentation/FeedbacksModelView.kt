package com.wayplaner.learn_room.feedbasks.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.feedbasks.data.repository.FeedbacksApiImpl
import com.wayplaner.learn_room.feedbasks.domain.model.FeedsDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbacksModelView @Inject constructor(
    private val feedbacksApiImpl: FeedbacksApiImpl
): ViewModel() {

    private val feedbacks_ = MutableLiveData<List<FeedsDTO>?>(mutableListOf())
    val feedbacks: LiveData<List<FeedsDTO>?> = feedbacks_
    fun getFeedbacks(idOrg: Long){
        viewModelScope.launch {
            feedbacks_.postValue(feedbacksApiImpl.feedbacksApi(idOrg).body())
        }
    }

}