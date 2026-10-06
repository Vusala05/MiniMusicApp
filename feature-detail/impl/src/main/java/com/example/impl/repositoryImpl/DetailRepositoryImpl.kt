
package com.example.impl.repositoryImpl

import com.example.api.reposotory.DetailRepository
import com.example.core_data.feauture.GlobalNetworkHandler
import javax.inject.Inject


class DetailRepositoryImpl @Inject constructor(
    val globalNetworkHandler: GlobalNetworkHandler,

): DetailRepository {

}

