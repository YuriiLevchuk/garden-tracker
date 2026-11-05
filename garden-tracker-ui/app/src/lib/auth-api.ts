import { getGardenDashboard, type User } from "@/lib/garden-api";

const TOKEN_KEY = "garden-tracker-token";

// Login is off during `npm run dev` until the app uses real data. Set VITE_AUTH_ENABLED=true in .env.local to turn it on.
// Production builds always require login, and the backend always requires a token.
const AUTH_DISABLED = import.meta.env.DEV && import.meta.env.VITE_AUTH_ENABLED !== "true";

type AuthResponse = {
  token: string;
  user: User;
};

export function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function logout() {
  localStorage.removeItem(TOKEN_KEY);
}

export function login(email: string, password: string) {
  return authenticate("/api/auth/login", email, password);
}

export function register(email: string, password: string) {
  return authenticate("/api/auth/register", email, password);
}

// Returns the logged-in user, or null when there is no valid session. An expired token is removed.
export async function getCurrentUser(): Promise<User | null> {
  if (AUTH_DISABLED) return getGardenDashboard().user;

  const token = getToken();
  if (!token) return null;

  const response = await fetch("/api/users/me", {
    headers: { Authorization: `Bearer ${token}` },
  });
  if (response.status === 401) logout();
  return response.ok ? response.json() : null;
}

async function authenticate(path: string, email: string, password: string) {
  if (AUTH_DISABLED) return;

  const response = await fetch(path, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password }),
  });
  if (!response.ok) throw new Error(await readError(response));

  const { token }: AuthResponse = await response.json();
  localStorage.setItem(TOKEN_KEY, token);
}

async function readError(response: Response) {
  if (response.status === 400) return "Check your email. The password must have 8 to 72 characters.";
  try {
    const problem: { detail?: string } = await response.json();
    if (problem.detail) return problem.detail;
  } catch {
    // No JSON body, e.g. the proxy could not reach the backend.
  }
  return "Something went wrong. Please try again.";
}
