async function getClients() {
    const response = await fetch('/api/getClients', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    return await response.json()
  }


  let clients = getClients();
  console.log(clients);


  let clients = getClients();
  console.log(clients);