import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';

function AccountDetails() {
  const { accountId } = useParams();
  const [account, setAccount] = useState(null);
  const [userName, setUserName] = useState('');

  useEffect(() => {
    async function fetchData() {
      const accountResponse = await fetch(`http://localhost:8080/api/accounts/${accountId}`);
      const accountData = await accountResponse.json();
      setAccount(accountData);

      const userResponse = await fetch(`http://localhost:8080/api/users/${accountData.userId}`);
      const userData = await userResponse.json();
      setUserName(userData.name);
    }
    fetchData();
  }, [accountId]);

  if (!account) return <p>Loading...</p>;

  return (
    <div>
      <h1>Account Details</h1>
      <p>Account ID: {account.id}</p>
      <p>User Name: {userName}</p>
      <p>Balance: {account.balance}</p>
      <Link to={`/accounts/${accountId}/deposit`}><button>Deposit</button></Link>
      <Link to={`/accounts/${accountId}/withdraw`}><button>Withdraw</button></Link>
      <Link to={`/accounts/${accountId}/transactions`}><button>View Transactions</button></Link>
    </div>
  );
}

export default AccountDetails;