import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function CreateAccount() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [accountType, setAccountType] = useState('SAVINGS');
  const navigate = useNavigate();

  async function handleSubmit(event) {
    event.preventDefault();

    const userResponse = await fetch('http://localhost:8080/api/users', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email }),
    });
    const user = await userResponse.json();

    const accountResponse = await fetch('http://localhost:8080/api/accounts', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userId: user.id, accountType }),
    });
    const account = await accountResponse.json();

    navigate(`/accounts/${account.id}`);
  }

  return (
    <div>
      <h1>Create Account</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Account Type</label>
          <select
            value={accountType}
            onChange={(e) => setAccountType(e.target.value)}
          >
            <option value="SAVINGS">Savings</option>
            <option value="CHECKING">Checking</option>
          </select>
        </div>
        <button type="submit">Create Account</button>
      </form>
    </div>
  );
}

export default CreateAccount;