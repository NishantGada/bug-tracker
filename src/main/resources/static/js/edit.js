// Get the modal
var modal = document.getElementById("editModal");

let editingTaskNumber = 0;

let taskTitle = document.getElementsByClassName('list-item-title');
let taskDesc = document.getElementsByClassName('list-item-desc');
let taskAssignee = document.getElementsByClassName('list-item-assignee');
// let taskDue = document.getElementsByClassName('list-item-due');

let editTaskTitle = document.getElementById('edit-task-title');
// let editTaskPriority = document.getElementById('edit-task-priority');
let editTaskDescription = document.getElementById('edit-task-description');
let editTaskAssignee = document.getElementById('edit-task-assignee');

var tasks = document.getElementsByClassName("list-item");

var span = document.getElementsByClassName("close")[1];

for (let i = 0; i < tasks.length; i++) {
    tasks[i].onclick = function () {
        console.log("Editing task: ", i);
        editingTaskNumber = i;
        modal.style.display = "block";

        editTaskTitle.value = taskTitle[i].innerText;
        // editTaskPriority.value = taskTitle[i].innerText;
        editTaskDescription.value = taskDesc[i].innerText;
        editTaskAssignee.value = taskAssignee[i].innerHTML;
    }
}

modal.addEventListener("submit", function (event) {
    event.preventDefault();

    // for (let i = 0; i < tasks.length; i++) {
    console.log(taskTitle[editingTaskNumber].innerText);
    console.log(taskDesc[editingTaskNumber].innerText);
    console.log(taskAssignee[editingTaskNumber].innerHTML);
    // }

    // editTaskTitle.value
    // editTaskDescription.value
    // editTaskAssignee.value

    console.log("SAVED!");
});

// When the user clicks on <span> (x), close the modal
span.onclick = function () {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}