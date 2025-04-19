// get authenticated User


async function auth() {
    let res = await fetch('http://localhost:8080/api/user');
    return await res.json();
}

authUserInfo();

currentUser();

// authenticated User email/role

async function authUserInfo() {
    let user = await auth();
    document.getElementById("adminEmail").textContent = user.email;
    let roles = "";
    user.roles.forEach(role => {
        roles += role.name.substring(5, role.name.length) + " ";
    })
    document.getElementById("adminRole").textContent = roles;
}

async function currentUser() {
    const tbody = document.querySelector('#currentUserBody');

    let user = await auth();
    let roles = user.roles.map(role => role.name.substring(5, role.name.length));
    let rolesInTable = '';
    roles.forEach(role => {
        rolesInTable += `<div>${role}</div>`
    });

    tbody.innerHTML = `<tr>
            <td class="align-middle">${user.id}</td>
            <td class="align-middle">${user.name}</td>
            <td class="align-middle">${user.surname}</td>
            <td class="align-middle">${user.age}</td>     
            <td class="align-middle">${user.email}</td>       
            <td class="align-middle">${rolesInTable}</td>
            </tr>`;
}