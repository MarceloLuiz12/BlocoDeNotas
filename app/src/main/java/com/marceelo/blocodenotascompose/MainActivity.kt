package com.marceelo.blocodenotascompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marceelo.blocodenotascompose.datastore.StoreAnnotation
import com.marceelo.blocodenotascompose.ui.theme.BlocoDeNotasComposeTheme
import com.marceelo.blocodenotascompose.ui.theme.DARK_BLUE
import com.marceelo.blocodenotascompose.ui.theme.WHITE
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlocoDeNotasComposeTheme {
                NotepadScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotepadScreen() {

    val context = LocalContext.current
    val storeAnnotation = StoreAnnotation(context)
    val scope = rememberCoroutineScope()

    var annotation by remember { mutableStateOf("") }
    annotation = storeAnnotation.getAnnotation.collectAsState(initial = "").value

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = DARK_BLUE,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Bloco de Notas",
                    color = WHITE,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        storeAnnotation.saveAnnotation(annotation)
                        Toast.makeText(context, "Anotação salva com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                },
                backgroundColor = DARK_BLUE,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp
                ),
                contentColor = WHITE
            ) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_save), contentDescription = "ícone de salvar anotação" )
            }
        }
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState()) ){
            TextField(
                modifier = Modifier.fillMaxSize(),
                value = annotation,
                onValueChange = { annotation = it },
                label = { Text("Digite a sua anotação...") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = WHITE,
                    cursorColor = DARK_BLUE,
                    focusedLabelColor = WHITE,
                    unfocusedIndicatorColor = WHITE,
                    focusedIndicatorColor = WHITE
                )
            )
        }
    }
}

@Composable
@Preview
private fun NotepadPreview() {
    NotepadScreen()
}