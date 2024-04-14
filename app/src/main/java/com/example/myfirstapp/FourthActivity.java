package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FourthActivity extends AppCompatActivity {

    // Deklaration af variabler til knapper, layoutcontainere og opgaveliste
    private Button buttonNextPage, buttonAddTask;
    private LinearLayout taskListContainer, taskListDoneContainer;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        // Initialiser views direkte i onCreate-metoden
        buttonNextPage = findViewById(R.id.buttonNextPage);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        taskListContainer = findViewById(R.id.taskListId);
        taskListDoneContainer = findViewById(R.id.taskListDoneId);

        // Opret en ny tom liste til opgaver
        taskList = new ArrayList<>();

        // Opsæt lyttere til knapperne
        setListeners();
    }

    // Opsæt lyttere til knapperne
    private void setListeners() {
        // Lytter til "Tilføj opgave" knappen
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });

        // Lytter til "Næste side" knappen
        buttonNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNextPage();
            }
        });
    }

    // Vis dialog til at tilføje en ny opgave
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        // Hent referencer til dialogens komponenter
        EditText editTextTaskName = view.findViewById(R.id.editTextTaskName);
        CheckBox highPriorityCheckBox = view.findViewById(R.id.HighPriorityId);
        CheckBox mediumPriorityCheckBox = view.findViewById(R.id.MediumPriorityId);
        CheckBox lowPriorityCheckBox = view.findViewById(R.id.LowPriorityId);
        Button saveButton = view.findViewById(R.id.buttonSaveTask);

        // Lytter til "Gem" knappen i dialogen
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hent opgaveoplysninger fra dialogen
                String taskName = editTextTaskName.getText().toString();
                boolean isHighPriority = highPriorityCheckBox.isChecked();
                boolean isMediumPriority = mediumPriorityCheckBox.isChecked();
                boolean isLowPriority = lowPriorityCheckBox.isChecked();

                // Vis en fejlmeddelelse, hvis ingen prioritet er valgt
                if (!isHighPriority && !isMediumPriority && !isLowPriority) {
                    Toast.makeText(FourthActivity.this, "Please select a priority", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Opret en ny opgave og tilføj den til listen
                Task task = new Task(taskName, isHighPriority, isMediumPriority, isLowPriority);
                taskList.add(task);

                // Sorter opgavelisten efter prioritet
                sortTaskList();

                // Luk dialogen
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // Sorter opgavelisten efter prioritet
    private void sortTaskList() {
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (task1.isHighPriority()) {
                    return -1;
                } else if (task2.isHighPriority()) {
                    return 1;
                } else if (task1.isMediumPriority()) {
                    return -1;
                } else if (task2.isMediumPriority()) {
                    return 1;
                } else if (task1.isLowPriority()) {
                    return -1;
                } else if (task2.isLowPriority()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        // Opdater visningen af opgavelisten
        updateTaskListUI();
    }

    // Opdater visningen af opgavelisten
    private void updateTaskListUI() {
        taskListContainer.removeAllViews();
        for (Task task : taskList) {
            addTaskToList(task.getName(), task.isHighPriority(), task.isMediumPriority(), task.isLowPriority());
        }
    }

    // Tilføj en opgave til opgavelisten
    private void addTaskToList(String taskName, boolean isHighPriority, boolean isMediumPriority, boolean isLowPriority) {
        LinearLayout taskLayout = new LinearLayout(this);
        taskLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Opret tekstvisning til opgaven
        TextView newTaskTextView = new TextView(this);
        newTaskTextView.setText(taskName);
        newTaskTextView.setTextSize(20);

        // Opret tekstvisning til prioritet
        TextView priorityTextView = new TextView(this);
        LinearLayout.LayoutParams priorityLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        priorityLayoutParams.setMargins(50, 0, 0, 0);
        priorityTextView.setLayoutParams(priorityLayoutParams);
        priorityTextView.setTextSize(20);
        setPriorityText(priorityTextView, isHighPriority, isMediumPriority, isLowPriority);

        // Opret afkrydsningsboks til opgaven
        CheckBox taskDoneCheckBox = new CheckBox(this);
        taskDoneCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Flyt opgaven til "Færdige opgaver" container, når den afkrydses
                taskListContainer.removeView(taskLayout);
                taskListDoneContainer.addView(taskLayout);
            }
        });

        // Tilføj komponenter til opgavelayout
        taskLayout.addView(newTaskTextView);
        taskLayout.addView(priorityTextView);
        taskLayout.addView(taskDoneCheckBox);
        taskLayout.setOnClickListener(v -> {
            // Flyt opgaven til "Færdige opgaver" container ved klik på opgaven
            taskListContainer.removeView(taskLayout);
            taskListDoneContainer.addView(taskLayout);
        });

        // Tilføj opgavelayout til opgavelisten
        taskListContainer.addView(taskLayout);
    }

    // Metode til at angive prioritetstekstfarve og -tekst ud fra prioritet
    private void setPriorityText(TextView textView, boolean isHighPriority, boolean isMediumPriority, boolean isLowPriority) {
        if (isHighPriority) {
            textView.setText(R.string.high_priority);
            textView.setTextColor(ContextCompat.getColor(this, R.color.high_priority));
        } else if (isMediumPriority) {
            textView.setText(R.string.medium_priority);
            textView.setTextColor(ContextCompat.getColor(this, R.color.medium_priority));
        } else if (isLowPriority) {
            textView.setText(R.string.low_priority);
            textView.setTextColor(ContextCompat.getColor(this, R.color.low_priority));
        }
    }

    // Metode til navigering til næste aktivitet
    private void navigateToNextPage() {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    // Indre klasse til repræsentation af en opgave
    private static class Task {
        private String name;
        private boolean highPriority;
        private boolean mediumPriority;
        private boolean lowPriority;

        // Konstruktør til oprettelse af en opgave
        public Task(String name, boolean highPriority, boolean mediumPriority, boolean lowPriority) {
            this.name = name;
            this.highPriority = highPriority;
            this.mediumPriority = mediumPriority;
            this.lowPriority = lowPriority;
        }

        // Metoder til at få opgaveattributter
        public String getName() {
            return name;
        }

        public boolean isHighPriority() {
            return highPriority;
        }

        public boolean isMediumPriority() {
            return mediumPriority;
        }

        public boolean isLowPriority() {
            return lowPriority;
        }
    }
}


