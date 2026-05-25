package com.example.unifi.data.repository

import com.example.unifi.data.model.CuadroSinoptico
import com.example.unifi.data.model.IdeaSinoptica
import com.example.unifi.data.model.SubideaSinoptica
import com.google.firebase.firestore.FirebaseFirestore

class CuadroSinopticoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("cuadros_sinopticos")

    fun agregarCuadro(
        titulo: String,
        conceptoGeneral: String,
        ideas: List<IdeaSinoptica>,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val cuadro = hashMapOf(
            "titulo" to titulo,
            "conceptoGeneral" to conceptoGeneral,
            "ideas" to ideas,
            "creadoEn" to System.currentTimeMillis()
        )

        collection
            .add(cuadro)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }

    fun obtenerCuadros(
        onResult: (List<CuadroSinoptico>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        collection
            .orderBy("creadoEn")
            .get()
            .addOnSuccessListener { result ->

                val cuadros = result.documents.map { document ->

                    val ideasFirestore =
                        document.get("ideas") as? List<Map<String, Any>>

                    val ideas = ideasFirestore?.map { ideaMap ->

                        val subideasFirestore =
                            ideaMap["subideas"] as? List<Map<String, Any>>

                        val subideas = subideasFirestore?.map { subideaMap ->
                            SubideaSinoptica(
                                titulo = subideaMap["titulo"] as? String ?: "",
                                detalles = subideaMap["detalles"] as? List<String> ?: emptyList()
                            )
                        } ?: emptyList()

                        IdeaSinoptica(
                            titulo = ideaMap["titulo"] as? String ?: "",
                            subideas = subideas
                        )
                    } ?: emptyList()

                    CuadroSinoptico(
                        id = document.id,
                        titulo = document.getString("titulo") ?: "",
                        conceptoGeneral = document.getString("conceptoGeneral") ?: "",
                        ideas = ideas,
                        creadoEn = document.getLong("creadoEn") ?: 0L
                    )
                }

                onResult(cuadros)
            }
            .addOnFailureListener { onError(it) }
    }

    fun eliminarCuadro(
        cuadroId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        collection
            .document(cuadroId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }
}