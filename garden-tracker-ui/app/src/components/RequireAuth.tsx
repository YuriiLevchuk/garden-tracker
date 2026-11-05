import { useEffect, useState } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { getCurrentUser } from "@/lib/auth-api";
import type { User } from "@/lib/garden-api";

// Lets only logged-in users reach nested routes, which read the user with useOutletContext.
export function RequireAuth() {
  const [user, setUser] = useState<User | null | undefined>(undefined);

  useEffect(() => {
    getCurrentUser().then(setUser).catch(() => setUser(null));
  }, []);

  if (user === undefined) return null;
  if (user === null) return <Navigate to="/login" replace />;
  return <Outlet context={user} />;
}
