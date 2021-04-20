/*document.getElementById("recherche").addEventListener("click", listeCommunes);
function listeCommunes() {
  let codeP = document.getElementById("lieuNaiss").value;
  const url =
    "https://geo.api.gouv.fr/communes?nom=" +
    codeP +
    "&fields=departement&limit=5";
  const fetchOptions = { method: "GET" };
  fetch(url + codeP, fetchOptions)
    .then((response) => {
      return response.json();
    })
    .then((dataJSON) => {
      console.log(dataJSON);
      let communes = dataJSON;
      let resHTML = "";
      let texteHTML = "";
      for (let c of communes) {
        resHTML += `<option>${c.departement.code}</option>`;
        texteHTML += `<option>${c.departement.nom}</option>`;
      }
      document.getElementById("codePostal").innerHTML = resHTML;
      document.getElementById("commune").innerHTML = texteHTML;
    })
    .catch((error) => {
      console.log(error);
    });
}*/
