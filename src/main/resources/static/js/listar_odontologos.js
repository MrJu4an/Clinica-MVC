const tableBody = document.querySelector("#odontologosTable tbody");
const fetchOdontologos = () => {
  // listando los odontologos

  fetch(`/odontologo`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
                <td>${odontologo.id}</td>
                <td>${odontologo.nombre}</td>
                <td>${odontologo.apellido}</td>
                <td>${odontologo.idMatricula}</td>
                <td>
                  <button class="btn btn-primary" onclick="editOdontologo(${odontologo.id}, '${odontologo.nombre}', '${odontologo.apellido}', '${odontologo.idMatricula}')" data-bs-toggle="modal" data-bs-target="#actualizarOdontologoModal" data-bs-whatever="@mdo"><i class='bx bx-edit-alt'></i></button>
                  <button class="btn btn-danger" onclick="deleteOdontologo(${odontologo.id})"><i class='bx bx-trash'></i></button>
                </td>
              `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}

// modificar un odontologo
const form = document.getElementById('actualizarForm')
form.addEventListener("submit", (event) => {
  event.preventDefault();

  const id = document.getElementById('idOdontologo').value
  const nombre = document.getElementById('nombreOdontologo').value
  const apellido = document.getElementById('apellidoOdontologo').value
  const matricula = document.getElementById('matriculaOdontologo').value

  // llamando al endpoint de actualizar
  fetch('/odontologo', {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({id, idMatricula: matricula, nombre, apellido})
  })
  .then(response => {
    if (!response.ok) {
      throw new Error(`Error en la solicitud: ${response.status}`)
    }
    return response.json();
  })
  .then((data) => {
    console.log(data);
    form.reset();
    Swal.fire({
      icon: "success",
      title: "Odontologo actualizado con exito",
      showConfirmButton: true,
      confirmButtonColor: "#0d6efd",
      confirmButtonText: "OK"
    })
    .then((result) => {
      if (result.isConfirmed) {
        location.replace('./listar_odontologos.html')
      }
    })
  })
  .catch((error) => {
    Swal.fire({
      icon: "error",
      title: "Ocurrio un error al actualizar el odontologo",
      text: `Error: ${error}`,
      confirmButtonColor: "#0d6efd",
    });
    console.error("Error actualizando odontologo:", error);
  });
})

const editOdontologo = (id, nombre, apellido, matricula) => {
  const idOdontologo = document.getElementById('idOdontologo')
  const nombreOdontologo = document.getElementById('nombreOdontologo')
  const apellidoOdontologo = document.getElementById('apellidoOdontologo')
  const matriculaOdontologo = document.getElementById('matriculaOdontologo')

  idOdontologo.value = id
  nombreOdontologo.value = nombre
  apellidoOdontologo.value = apellido
  matriculaOdontologo.value = matricula
}

// eliminar un odontologo
const deleteOdontologo = (id) => {
  Swal.fire({
    title: `¿Esta seguro de eliminar el registro con ID: ${id}?`,
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#0d6efd",
    cancelButtonColor: "#d33",
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar"
  }).then((result) => {
    if (result.isConfirmed) {

      fetch(`/odontologo/${id}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        }
      })
      .then(response => {
        if (!response.ok) {
          throw new Error(`Error en la solicitud: ${response.status}`)
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);
        Swal.fire({
          title: "¡Eliminado!",
          text: "El registro fue eliminado con exito",
          confirmButtonColor: "#0d6efd",
          icon: "success"
        })
        .then((result) => {
          if (result.isConfirmed) {
            location.replace('./listar_odontologos.html')
          }
        });
      })
      .catch((error) => {
        Swal.fire({
          icon: "error",
          title: "Ocurrio un error al eliminar el odontologo",
          text: `Error: ${error}`,
          confirmButtonColor: "#0d6efd",
        });
        console.error("Error eliminando odontologo:", error);
      });
    }
  });
}

//Buscamos los odontologos
fetchOdontologos();