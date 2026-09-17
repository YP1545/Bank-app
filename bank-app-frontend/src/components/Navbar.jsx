import { NavLink, useNavigate } from 'react-router-dom';
import { isLoggedIn, getRole, clearToken } from '../utils/auth';

function Navbar() {
  const loggedIn = isLoggedIn();
  const role = getRole();
  const navigate = useNavigate();

  function handleLogout() {
    clearToken();
    navigate('/login');
  }

  if (!loggedIn) {
    return (
      <nav className="navbar">
        <ul className="nav-links">
          <li><NavLink to="/login">Login</NavLink></li>
          <li><NavLink to="/register">Register</NavLink></li>
        </ul>
      </nav>
    );
  }

  return (
    <nav className="navbar">
      <ul className="nav-links">
        <li><NavLink to="/">Home</NavLink></li>
        <li><NavLink to="/about">About</NavLink></li>
        <li><NavLink to="/create-account">Create Account</NavLink></li>
        <li><NavLink to="/my-accounts">My Accounts</NavLink></li>
        {role === 'ADMIN' && (
          <li><NavLink to="/admin">Admin</NavLink></li>
        )}
        <li><button onClick={handleLogout}>Logout</button></li>
      </ul>
    </nav>
  );
}

export default Navbar;