import { Navigate } from 'react-router-dom';
import { isLoggedIn, getRole } from '../utils/auth';

function AdminRoute({ children }) {
  if (!isLoggedIn()) {
    return <Navigate to="/login" replace />;
  }
  if (getRole() !== 'ADMIN') {
    return <Navigate to="/" replace />;
  }
  return children;
}

export default AdminRoute;