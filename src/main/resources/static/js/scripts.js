let projectCode = 'someProjectCode'; // This will be updated dynamically

document.addEventListener('DOMContentLoaded', (event) => {
    const projectSelect = document.getElementById('project-select');
    if (projectSelect.options.length > 0) {
        projectCode = projectSelect.options[0].value;
    }
    var elems = document.querySelectorAll('select');
    var instances = M.FormSelect.init(elems);
});

function updateProjectCode() {
    const projectSelect = document.getElementById('project-select');
    projectCode = projectSelect.value;
    console.log('Project code updated to:', projectCode);
}