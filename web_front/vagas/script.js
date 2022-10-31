const parkingSpotUrl = 'http://localhost:8080/parking-spot/5';

const table = document.getElementById('table');
const tableBody = document.getElementById('table-body');

async function getparkingSpot() {
    const response = await fetch(parkingSpotUrl);
    if (response.ok) {
        const parkingSpots = await response.json();

        if (parkingSpots.lenght > 0){
            table.removeAttribute('hidden');
            parkingSpots.foreach((parkingSpot) => {
                createRow(parkingSpot);
            })
        }
       
    }
}

function createRow({id , responsibleName }){
    const row = document.createElement('tr');
    const idCollumn = document.createElement('th');
    const responsibleNameCollumn = document.createElement('td');
   // const parkingSpotNumberCollum = document.createElement('th');

    idCollumn.textContent = id;
    idCollumn.setAttribute("scope","row");

    responsibleNameCollumn.textContent = responsibleName;

  //  parkingSpotNumberCollum.textContent = parkingSpotNumber;

    
    
    row.appendChild(idCollumn);
    row.appendChild(responsibleNameCollumn);
   // row.appendChild(parkingSpotNumberCollum);

    tableBody.appendChild(row);

}


getparkingSpot();
