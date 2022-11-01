const table = $('#listOfParkings')


async function showAllParkings(){
    const response = await fetch("http://localhost:8080/parking-spot")
    if(response.ok){
        const parkings = await response.json()
        parkings.content.forEach((parking) => {
            createRow(parking)
        });
    }
}

function createRow({id, parkingSpotNumber, licensePlateCar, brandCar, responsibleName}){
    const row = $('<tr></tr>')
    const idColumn = $('<td></td>')
    const parkingSpotNumberColumn = $('<td></td>')
    const licensePlateCarColumn = $('<td></td>')
    const brandCarColumn = $('<td></td>')
    const responsibleNameColumn = $('<td></td>')

    console.log(responsibleName)


    row.attr('class', 'parking-row')
    idColumn.attr('scope','row')
    idColumn.text(id)
    parkingSpotNumberColumn.text(parkingSpotNumber)
    licensePlateCarColumn.text(licensePlateCar)
    brandCarColumn.text(brandCar)
    responsibleNameColumn.text(responsibleName)

    row.append(idColumn)
    row.append(parkingSpotNumberColumn)
    row.append(licensePlateCarColumn)
    row.append(brandCarColumn)
    row.append(responsibleNameColumn)

    console.log(responsibleNameColumn.text())

    table.append(row)
}

showAllParkings()


