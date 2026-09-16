import { useState } from 'react';

function Data() {
  const [customers, setCustomers] = useState([]);

  async function handleShowAllCustomers() {
    const response = await fetch('http://localhost:8080/api/users');
    const data = await response.json();
    setCustomers(data);
  }

  return (
    <div>
      <h1>Data</h1>
      <button onClick={handleShowAllCustomers}>Show All Customers</button>
      <div>
        {customers.map((c) => (
          <p key={c.id}>{c.name} - {c.email}</p>
        ))}
      </div>
    </div>
  );
}

export default Data;