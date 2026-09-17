import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { authFetch } from '../utils/auth';
import { API_BASE_URL } from '../utils/api';

function MyAccounts() {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchAccounts() {
      const response = await authFetch(`${API_BASE_URL}/api/accounts/my`);
      const data = await response.json();
      setAccounts(data);
      setLoading(false);
    }
    fetchAccounts();
  }, []);

  if (loading) return <p>Loading...</p>;

  return (
    <div>
      <h1>My Accounts</h1>
      {accounts.length === 0 && <p>You don't have any accounts yet.</p>}
      <div className="account-list">
        {accounts.map((account) => (
          <div className="account-list-item" key={account.id}>
            <Link to={`/accounts/${account.id}`}>
              {account.accountType} — Balance: {account.balance}
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyAccounts;
