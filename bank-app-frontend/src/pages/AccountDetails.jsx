import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { authFetch } from '../utils/auth';
import { API_BASE_URL } from '../utils/api';

function AccountDetails() {
  const { accountId } = useParams();
  const [account, setAccount] = useState(null);

  useEffect(() => {
    async function fetchAccount() {
      const response = await authFetch(`${API_BASE_URL}/api/accounts/${accountId}`);
      const data = await response.json();
      setAccount(data);
    }
    fetchAccount();
  }, [accountId]);

  if (!account) return <p>Loading...</p>;

  return (
    <div>
      <h1>Account Details</h1>
      <p>Account ID: {account.id}</p>
      <p>User Name: {account.userName}</p>
      <p>Account Type: {account.accountType}</p>
      <p>Balance: {account.balance}</p>
      <Link to={`/accounts/${accountId}/deposit`}>
        <button>Deposit</button>
      </Link>
      <Link to={`/accounts/${accountId}/withdraw`}>
        <button>Withdraw</button>
      </Link>
      <Link to={`/accounts/${accountId}/transactions`}>
        <button>View Transactions</button>
      </Link>
      <p>
        <Link to="/my-accounts">&larr; Back to My Accounts</Link>
      </p>
    </div>
  );
}

export default AccountDetails;
