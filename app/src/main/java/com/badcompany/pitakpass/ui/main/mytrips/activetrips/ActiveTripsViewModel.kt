package com.badcompany.pitakpass.ui.main.mytrips.activetrips

import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.DriverPost
import com.badcompany.pitakpass.domain.usecases.DeleteDriverPost
import com.badcompany.pitakpass.domain.usecases.FinishDriverPost
import com.badcompany.pitakpass.domain.usecases.GetActiveDriverPost
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.AppPreferences
import com.badcompany.pitakpass.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

class ActiveTripsViewModel @Inject constructor(val getActiveDriverPost: GetActiveDriverPost,
                                               val deletePost: DeleteDriverPost,
                                               val finishPost: FinishDriverPost) : BaseViewModel() {

    val activePostsResponse = SingleLiveEvent<ResultWrapper<List<DriverPost>>>()
    val deletePostReponse = SingleLiveEvent<ResultWrapper<Int>>()
    val finishPostResponse = SingleLiveEvent<ResultWrapper<Int>>()

    @ExperimentalSplittiesApi
    fun getActivePosts() {
        activePostsResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = getActiveDriverPost.execute(hashMapOf(
                Pair(Constants.TXT_TOKEN, AppPreferences.token),
                Pair(Constants.TXT_LANG, AppPreferences.language)))

            withContext(Dispatchers.Main) {
                activePostsResponse.value = response
            }
        }
    }

    @ExperimentalSplittiesApi
    fun deletePost(identifier: String, pos: Int) {
        deletePostReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = deletePost.execute(hashMapOf(
                Pair(Constants.TXT_TOKEN, AppPreferences.token),
                Pair(Constants.TXT_ID, identifier),
                Pair(Constants.TXT_POSITION, pos)))

            withContext(Dispatchers.Main) {
                deletePostReponse.value = response
            }
        }
    }

    @ExperimentalSplittiesApi
    fun finishPost(identifier: String, pos: Int) {
        finishPostResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = finishPost.execute(hashMapOf(
                Pair(Constants.TXT_TOKEN, AppPreferences.token),
                Pair(Constants.TXT_ID, identifier),
                Pair(Constants.TXT_POSITION, pos)))

            withContext(Dispatchers.Main) {
                finishPostResponse.value = response
            }
        }
    }

}