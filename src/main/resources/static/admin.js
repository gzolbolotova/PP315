const url = 'http://localhost:8080/api/admin'

function getUserData() {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            getAllUsers(data)
        })
}

function getAllUsers() {
    fetch(url).then(response => response.json()).then(user =>
        loadTable(user))
}

function loadTable(listAllUsers) {
    let res = '';
    for (let user of listAllUsers) {
        res +=
            `<tr>
                <td>${user.id}</td>             
                <td>${user.name}</td>
                <td>${user.lastname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(function (role) {
                return JSON.stringify(role.name).replaceAll('"', '').substring(5)
            })}</td>
                <td>
                    <button class="btn btn-info" type="button"
                    data-bs-toggle="modal" data-bs-target="#editModal"
                    onclick="editModal(${user.id})">Edit</button></td>
                <td>
                    <button class="btn btn-danger" type="button"
                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                    onclick="deleteModal(${user.id})">Delete</button></td>
            </tr>`
    }
    document.getElementById('allUsersTable').innerHTML = res;
}

getAllUsers();

//Edit
function editModal(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {

            document.getElementById('editId').value = u.id;
            document.getElementById('editUserName').value = u.name;
            document.getElementById('editLastname').value = u.lastname;
            document.getElementById('editAge').value = u.age;
            document.getElementById('editEmail').value = u.email;
            document.getElementById('editPassword').value = u.password;
        })
    });
}


async function editUser() {
    const form_ed = document.getElementById('modalEdit');

    let idValue = document.getElementById("editId").value;
    let userNameValue = document.getElementById("editUserName").value;
    let lastnameValue = document.getElementById("editLastname").value;
    let ageValue = document.getElementById('editAge').value;
    let emailValue = document.getElementById('editEmail').value;
    let passwordValue = document.getElementById("editPassword").value;
    let listOfRole = [];
    for (let i = 0; i < form_ed.role.options.length; i++) {
        if (form_ed.role.options[i].selected) {
            let tmp = {};
            tmp["id"] = form_ed.role.options[i].value
            listOfRole.push(tmp);
        }
    }
    let user = {
        id: idValue,
        name: userNameValue,
        lastname: lastnameValue,
        age: ageValue,
        email: emailValue,
        password: passwordValue,
        roles: listOfRole
    }
    await fetch(url + '/' + user.id, {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
    closeModal()
    getUserData()
}

//Delete
function deleteModal(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {
            document.getElementById('deleteId').value = u.id;
            document.getElementById('deleteUserName').value = u.name;
            document.getElementById('deleteLastname').value = u.lastname;
            document.getElementById('deleteAge').value = u.age;
            document.getElementById('deleteEmail').value = u.email;
            document.getElementById('deleteRole').value = u.roles.map(function (role) {
                return JSON.stringify(role.name).replaceAll('"', '').substring(5)
            });
        })
    });
}

async function deleteUser() {
    const id = document.getElementById("deleteId").value
    console.log(id)
    let urlDel = url + "/" + id;
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    }

    fetch(urlDel, method).then(() => {
        closeModal()
        getUserData()
    })
}


//Close modal
function closeModal() {
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}

//New User


document.getElementById('newUserForm').addEventListener('submit', (e) => {
    e.preventDefault()
    let role = document.getElementById('role_select')
    let roles = []
    let rolesAddUserValue = ''
    for (let i = 0; i < role.options.length; i++) {
        if (role.options[i].selected) {
            roles.push({id: role.options[i].value, role: 'ROLE_' + role.options[i].text})
            rolesAddUserValue += role.options[i].innerHTML

        }
    }
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            name: document.getElementById('newUserName').value,
            lastname: document.getElementById('newLastname').value,
            age: document.getElementById('newAge').value,
            email: document.getElementById('newEmail').value,
            password: document.getElementById('newPassword').value,
            roles: roles
        })
    })
        .then((response) => {
            if (response.ok) {
                getUserData();
                $('#newUserForm')[0].reset();
                document.getElementById("myUsers").click()
            }
        })
})


