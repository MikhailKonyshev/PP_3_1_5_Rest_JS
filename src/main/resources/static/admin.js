const urlLogin = 'http://localhost:8080/admin/users/principal';
const urlUsers = 'http://localhost:8080/admin/users';


async function getHeader() {
    try {
        const data = await fetch(urlLogin);
        const authenticationUser = await data.json();
        const authenticationEmail = document.getElementById('authenticationEmail');
        const authenticationRoles = document.getElementById('authenticationRoles');
        authenticationEmail.innerHTML = `${authenticationUser.email}`;
        authenticationRoles.innerHTML = 'with roles: ' + `${authenticationUser.roles.map(role => role.role.substring(5)).join(' ')}`;
    } catch (e) {
        console.error(e);
    }
}


async function getAllUsersTable() {
    try {
        const data = await fetch(urlUsers);
        const users = await data.json();
        const table = document.getElementById("tbody");
        let dataHtml = '';
        users.forEach(user => {
            dataHtml +=
                `<tr>
                 <td>${user.id}</td>
                 <td>${user.username}</td>
                 <td>${user.lastName}</td>
                 <td>${user.age}</td>
                 <td>${user.email}</td>
                 <td>${user.roles.map(role => role.role.substring(5)).join(' ')}</td>
                 <td>
                        <button id="buttonEdit" type="button" class="buttonEdit btn btn-primary" data-toggle="modal"
                        onclick="openEdit(${user.id})"  data-target="#edit">Edit</button>
                    </td>
                 <td>
                        <button id="buttonDelete" type="button" class="buttonDelete btn btn-danger" data-toggle="modal"
                        onclick="openDelete(${user.id})" 
                        data-target="#delete">Delete</button>
                 </td>
            </tr>`
            table.innerHTML = dataHtml;
        })
    } catch (e) {
        console.error(e);
    }
}

async function getUserTable() {
    try {
        const data = await fetch(urlLogin);
        const user = await data.json();
        const table = document.getElementById("tbodyUser");
        let dataHtml = '';

            dataHtml =
                `<tr>
                 <td>${user.id}</td>
                 <td>${user.username}</td>
                 <td>${user.lastName}</td>
                 <td>${user.age}</td>
                 <td>${user.email}</td>
                 <td>${user.roles.map(role => role.role.substring(5)).join(' ')}</td>
                
            </tr>`
            table.innerHTML = dataHtml;

    } catch (e) {
        console.error(e);
    }
}


async function getUser(id) {
    const data = await fetch('/admin/users/' + id)
    return await data.json()
}


async function getAllRoles(selectRole) {

    const data = await fetch("http://localhost:8080/admin/roles");
    const roles = await data.json();
    let temp = "";
    await roles.forEach(role =>
            {
                temp += `<option value="${role.id}">${role.role.substring(5)}</option>`
            })
            selectRole.setAttribute('size', roles.length);
            selectRole.innerHTML= temp;

}


function getSelectedRole(selectTag,userRoles) {
    let temp = "";
    let counr1 = [];
            for (let role of userRoles) {
                    temp += `<option value="${role.id}">${role.role.substring(5)}</option>`
                    counr1.push(role.role)
            }
            selectTag.setAttribute('size', counr1.length );
            selectTag.innerHTML = temp;
}


const rolesDelete = document.getElementById('deleteModalRoles');
const rolesEdit = document.getElementById('editModalRoles');
const rolesNew = document.getElementById('rolesNew');


const formD = document.forms['deleteModalForm']
const form = document.forms['editModalForm']
const formNewUser = document.forms['newUserForm']

async function openEdit(id){
    try {
        $('#editModal').modal('show');
        const user = await getUser(id);
        form.id.value = user.id;
        form.username.value = user.username;
        form.lastName.value = user.lastName;
        form.age.value = user.age;
        form.email.value = user.email;
        getAllRoles(rolesEdit);
        form.roles.value;
        form.addEventListener("submit", event => editUser(event, user.id).then(getAllUsersTable).then(closeEditModal), {
            once: true
        });
    } catch (e){
        console.error(e);
    }
}

async function editUser(event, id) {
    event.preventDefault();
    let listOfRole = [];
    for (let i = 0; i < form.roles.options.length; i++) {
        if (form.roles.options[i].selected) {
            listOfRole.push({id: form.roles.options[i].value,
                role: 'ROLE_' + form.roles.options[i].text});
        }
    }

    await fetch("http://localhost:8080/admin/users/" + id, {
        method: "PUT",
        body: JSON.stringify({
            "id":  form.id.value,
            "username": form.username.value,
            "lastName": form.lastName.value,
            "age": form.age.value,
            "email": form.email.value,
            "password": form.password.value,
            "roles": listOfRole,
        }),
        headers: {
            "Content-Type": "application/json",
        }
    })
}


async function openDelete(id){

    try {
        $('#deleteModal').modal('show');
        const user = await getUser(id);
        formD.id.value = user.id;
        formD.username.value = user.username;
        formD.lastName.value = user.lastName;
        formD.age.value = user.age;
        formD.email.value = user.email;
        getSelectedRole(rolesDelete,user.roles)
        formD.roles.value
        formD.addEventListener("submit",event => deleteUser(event, user.id).then(getAllUsersTable).then(closeDeleteModal), {
            once: true
        });

    } catch (e) {
        console.error(e);
    }
}

async function deleteUser(event, id) {

    event.preventDefault();

    await fetch("http://localhost:8080/admin/users/" + id, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json'
        }
    })
}



function closeDeleteModal() {
    $('#deleteModal').modal('hide');
}

function closeEditModal() {
    $('#editModal').modal('hide');
}


getAllRoles(rolesNew);

async function newUser(event) {
    event.preventDefault();

    let listOfRole = [];
    for (let i = 0; i < formNewUser.roles.options.length; i++) {
        if (formNewUser.roles.options[i].selected) {
            listOfRole.push({id: formNewUser.roles.options[i].value,
                role: 'ROLE_'+ formNewUser.roles.options[i].text});
        }
    }

    await fetch("http://localhost:8080/admin/users/", {
        method: "POST",
        body: JSON.stringify({
            "id":  formNewUser.id.value,
            "username": formNewUser.username.value,
            "lastName": formNewUser.lastName.value,
            "age": formNewUser.age.value,
            "email": formNewUser.email.value,
            "password": formNewUser.password.value,
            "roles": listOfRole,
        }),
        headers: {
            "Content-Type": "application/json",
        }
    })
}

formNewUser.addEventListener("submit", event => {
    newUser(event).then(() => {
        formNewUser.reset();
         getAllUsersTable();
        $('#usersTable-tab').click();
    });
});

getUserTable();
getHeader();
getAllUsersTable();




