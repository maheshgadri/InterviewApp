package com.charvi.interview.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.charvi.interview.viewmodel.InterviewViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewForm(viewModel: InterviewViewModel = viewModel()) {
    val name by viewModel.name.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val designation by viewModel.designation.collectAsState()
    val industry by viewModel.industry.collectAsState()
    val yearsOfExperience by viewModel.yearsOfExperience.collectAsState()
    val interviewQuestions by viewModel.interviewQuestions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lastName,
            onValueChange = { viewModel.onLastNameChange(it) },
            label = { Text("Last Name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuField(
            value = designation,
            onValueChange = { viewModel.onDesignationChange(it) },
            label = "Designation",
            options = listOf("Manager", "Developer", "Designer")
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuField(
            value = industry,
            onValueChange = { viewModel.onIndustryChange(it) },
            label = "Industry",
            options = listOf("IT", "Finance", "Healthcare")
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = yearsOfExperience,
            onValueChange = { viewModel.onYearsOfExperienceChange(it) },
            label = { Text("Years of Experience") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.generateInterviewQuestions() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Generate Interview Questions")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Interview Questions:")
        interviewQuestions.forEach { question ->
            Text(text = question)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.width(280.dp)) {
        TextField(
            value = value,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = true }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },

        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}
