const form = document.getElementById("agregarForm");

form.addEventListener("submit", (event) => {
  event.preventDefault();

  const nombre = document.getElementById("nombre").value;
  const apellido = document.getElementById("apellido").value;
  const matricula = document.getElementById("matricula").value;

  // llamando al endpoint de agregar

  fetch(`/odontologo`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ idMatricula: matricula, nombre, apellido }),
  })
  .then(response => {
    if (!response.ok) {
      throw new Error(`Error en la solicitud: ${response.status}`)
    }
    return response.json();
  })
  .then((data) => {
    console.log(data);
    //alert("Odontólogo agregado con éxito");
    form.reset(); 
    Swal.fire({
      icon: "success",
      title: "Odontologo creado con exito",
      showConfirmButton: true,
      confirmButtonColor: "#0d6efd",
      confirmButtonText: "OK"
    }).then((result) => {
      if (result.isConfirmed) {
        location.replace('./listar_odontologos.html')
      }
    })
  })
  .catch((error) => {
    Swal.fire({
      icon: "error",
      title: "Ocurrio un error al crear el odontologo",
      text: `Error: ${error}`,
      confirmButtonColor: "#0d6efd",
    });
    console.error("Error creando odontologo:", error);
  });
});