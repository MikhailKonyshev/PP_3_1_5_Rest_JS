const urlLoginUser = 'http://localhost:8080/admin/users/principal';

async function getHeader() {
    try {
        const data = await fetch(urlLoginUser);
        const authenticationUser = await data.json();
        const authenticationEmail = document.getElementById('authenticationEmail');
        const authenticationRoles = document.getElementById('authenticationRoles');
        authenticationEmail.innerHTML = `${authenticationUser.email}`;
        authenticationRoles.innerHTML = 'with roles: ' + `${authenticationUser.roles.map(role => role.role.substring(5)).join(' ')}`;
    } catch (e) {
        console.error(e);
    }
}

async function getUserTable() {
    try {
        const data = await fetch(urlLoginUser);
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

getHeader();
getUserTable();