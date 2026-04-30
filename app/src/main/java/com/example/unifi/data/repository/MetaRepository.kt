package com.example.unifi.data.repository

import com.example.unifi.data.model.Meta

class MetaRepository {

    private val metas = mutableListOf<Meta>()

    fun getMetas(): List<Meta> = metas

    fun addMeta(meta: Meta) {
        metas.add(meta)
    }

    fun deleteMeta(meta: Meta) {
        metas.remove(meta)
    }

    fun updateMeta(metaModificada: Meta) {
        val index = metas.indexOfFirst { it.id == metaModificada.id }
        if (index != -1) {
            metas[index] = metaModificada
        }
    }
}