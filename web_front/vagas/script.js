const parkingSpotUrl = 'http://localhost:8080/parking-spot/5';

const table = document.getElementById('table');
const tableBody = document.getElementById('table-body');

async function getparkingSpot() {
    const response = await fetch(parkingSpotUrl);
    if (response.ok) {
        const tb_parking_spot = await response.json();

        if (tb_parking_spot.lenght > 0) {
            table.removeAttribute('hidden');
            tb_parking_spot.foreach((tb_parking_spot) => {
                createRow(tb_parking_spot);
            })
        }

    }
}

function createRow({ id, responsibleName }) {
    const row = document.createElement('tr');
    const idCollumn = document.createElement('th');
    const responsibleNameCollumn = document.createElement('td');


    idCollumn.textContent = id;
    idCollumn.setAttribute("scope", "row");

    responsibleNameCollumn.textContent = responsibleName;


    row.appendChild(idCollumn);
    row.appendChild(responsibleNameCollumn);


    tableBody.appendChild(row);

}


getparkingSpot();
