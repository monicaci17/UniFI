package com.example.unifi.ui.screens.relajacion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unifi.data.model.Frases
import com.example.unifi.viewmodel.RelajacionViewModel

@Composable
fun RelajacionScreen(
    relajacionViewModel: RelajacionViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Relajación y Motivación",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                relajacionViewModel.cargarFrases()
            }
        ) {
            Text("Cargar nuevas frases")
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(relajacionViewModel.frases) { frase ->
                FraseCard(frase)
            }
        }
    }
}

@Composable
fun FraseCard(frase: Frases) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = "\"${frase.q}\"",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "- ${frase.a}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}