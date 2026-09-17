import { useState, useEffect } from 'react';
import { authFetch } from '../utils/auth';
import { API_BASE_URL } from '../utils/api';

function Admin() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchCustomers() {
      const response = await authFetch(`${API_BASE_URL}/api/users`);
      const data = await response.json();
      setCustomers(data);
      setLoading(false);
    }
    fetchCustomers();
  }, []);

  return (
    <div>
      <h1>Admin — All Customers</h1>
      {loading && <p>Loading...</p>}
      {!loading && (
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Role</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((c) => (
              <tr key={c.id}>
                <td>{c.name}</td>
                <td>{c.email}</td>
                <td>{c.role}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default Admin;