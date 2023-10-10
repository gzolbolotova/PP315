const userUrl = 'http://localhost:8080/api/curUser';


function getUserPage() {
    fetch(userUrl).then(response => response.json()).then(user =>
        getInformationAboutUser(user))
}


function getInformationAboutUser(user) {

    let result = '';
    result =

        `<tr>
    <td>${user.id}</td>
    <td>${user.name}</td>
    <td>${user.lastname}</td>
    <td>${user.age}</td>
    <td>${user.email}</td>
    <td>${user.roles.map(function (role) {
            return JSON.stringify(role.name).replaceAll('"', '').substring(5)
        })}</td>
</tr>`
    document.getElementById('userBody').innerHTML = result;
}

getUserPage();

