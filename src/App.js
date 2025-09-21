import React, { useState, useEffect } from "react";

const STATUS_OPTIONS = ["pending", "complete"];
const FILTER_MAP = {
  All: () => true,
  Pending: (task) => task.status === "pending",
  Completed: (task) => task.status === "complete",
};
const FILTER_NAMES = Object.keys(FILTER_MAP);

export default function App() {
  // Auth states
  const [authPage, setAuthPage] = useState("login");
  const [currentUser, setCurrentUser] = useState(null);

  // Form states
  const [loginUsername, setLoginUsername] = useState("");
  const [loginPassword, setLoginPassword] = useState("");
  const [loginError, setLoginError] = useState("");

  const [regUsername, setRegUsername] = useState("");
  const [regPassword, setRegPassword] = useState("");
  const [regError, setRegError] = useState("");
  const [regSuccess, setRegSuccess] = useState("");

  const [forgotUsername, setForgotUsername] = useState("");
  const [forgotNewPassword, setForgotNewPassword] = useState("");
  const [forgotError, setForgotError] = useState("");
  const [forgotSuccess, setForgotSuccess] = useState("");

  const [tasks, setTasks] = useState([]);
  const [filter, setFilter] = useState("All");
  const [newTask, setNewTask] = useState({
    title: "",
    description: "",
    dueDate: "",
    status: "pending",
  });
  const [editId, setEditId] = useState(null);
  const [editTask, setEditTask] = useState({
    title: "",
    description: "",
    dueDate: "",
    status: "pending",
  });

  useEffect(() => {
    const loggedInUser = localStorage.getItem("loggedInUser");
    if (loggedInUser) {
      setCurrentUser(loggedInUser);
      setAuthPage("dashboard");
    }
  }, []);

  useEffect(() => {
    if (currentUser) {
      const data = localStorage.getItem("tasks_" + currentUser);
      setTasks(data ? JSON.parse(data) : []);
    }
  }, [currentUser]);

  function handleLogin(e) {
    e.preventDefault();
    setLoginError("");
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    const user = users.find(
      (u) => u.username === loginUsername && u.password === loginPassword
    );
    if (user) {
      setCurrentUser(user.username);
      localStorage.setItem("loggedInUser", user.username);
      setAuthPage("dashboard");
      setLoginUsername("");
      setLoginPassword("");
    } else {
      setLoginError("Invalid username or password");
    }
  }

  function handleRegister(e) {
    e.preventDefault();
    setRegError("");
    setRegSuccess("");
    if (!regUsername || !regPassword) {
      setRegError("Fill all fields");
      return;
    }
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    if (users.some((u) => u.username === regUsername)) {
      setRegError("Username already registered!");
      return;
    }
    users.push({ username: regUsername, password: regPassword });
    localStorage.setItem("users", JSON.stringify(users));
    setRegSuccess("Registered successfully! Please login.");
    setRegUsername("");
    setRegPassword("");
    setTimeout(() => setAuthPage("login"), 1200);
  }

  function handleForgot(e) {
    e.preventDefault();
    setForgotError("");
    setForgotSuccess("");
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    const idx = users.findIndex((u) => u.username === forgotUsername);
    if (idx === -1) {
      setForgotError("User not found.");
      return;
    }
    users[idx].password = forgotNewPassword;
    localStorage.setItem("users", JSON.stringify(users));
    setForgotSuccess("Password reset successful! Please login.");
    setForgotUsername("");
    setForgotNewPassword("");
    setTimeout(() => setAuthPage("login"), 1200);
  }

  function handleLogout() {
    localStorage.removeItem("loggedInUser");
    setCurrentUser(null);
    setAuthPage("login");
    setTasks([]);
  }

  function saveTasks(updated) {
    setTasks(updated);
    localStorage.setItem("tasks_" + currentUser, JSON.stringify(updated));
  }

  function addTask(e) {
    e.preventDefault();
    if (!newTask.title.trim() || !newTask.description.trim() || !newTask.dueDate) return;
    saveTasks([
      ...tasks,
      { id: Date.now(), ...newTask }
    ]);
    setNewTask({
      title: "",
      description: "",
      dueDate: "",
      status: "pending",
    });
  }

  function deleteTask(id) {
    saveTasks(tasks.filter((t) => t.id !== id));
    setEditId(null);
    setEditTask({
      title: "",
      description: "",
      dueDate: "",
      status: "pending",
    });
  }

  function startEdit(task) {
    setEditId(task.id);
    setEditTask({
      title: task.title,
      description: task.description,
      dueDate: task.dueDate,
      status: task.status,
    });
  }

  function updateTask(e) {
    e.preventDefault();
    saveTasks(
      tasks.map((t) =>
        t.id === editId
          ? { ...t, ...editTask }
          : t
      )
    );
    setEditId(null);
    setEditTask({
      title: "",
      description: "",
      dueDate: "",
      status: "pending",
    });
  }

  function markComplete(id) {
    saveTasks(tasks.map((t) => (t.id === id ? { ...t, status: "complete" } : t)));
  }

  function markPending(id) {
    saveTasks(tasks.map((t) => (t.id === id ? { ...t, status: "pending" } : t)));
  }

  // --- Professional Login Page ---
  if (authPage === "login" && !currentUser) {
    return (
      <div style={{
        minHeight: "100vh",
        background: "linear-gradient(120deg, #ecf0f3 70%, #dbeafe 100%)",
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
      }}>
        <div style={{
          maxWidth: 400,
          width: "100%",
          background: "#fff",
          borderRadius: 14,
          boxShadow: "0 4px 32px rgba(60,80,210,.11)",
          padding: "34px 38px",
          textAlign: "center",
        }}>
          <h2 style={{
            color: "#2563eb",
            fontWeight: 800,
            marginBottom: 28,
            letterSpacing: ".03em"
          }}>
            Task Manager Login
          </h2>
          <form onSubmit={handleLogin} style={{ marginBottom: 12 }}>
            <label style={{ fontWeight: 600, marginBottom: 12, textAlign: "left" }}>
              Username
              <input
                type="text"
                value={loginUsername}
                onChange={e => setLoginUsername(e.target.value)}
                style={{
                  padding: "8px 12px",
                  marginTop: 7,
                  marginBottom: 12,
                  width: "100%",
                  border: "2px solid #e0e7ee",
                  borderRadius: "6px",
                  fontSize: "1.05rem",
                  background: "#f4f7fa"
                }}
                placeholder="Enter your username"
                autoFocus
              />
            </label>
            <label style={{ fontWeight: 600, marginBottom: 20, textAlign: "left" }}>
              Password
              <input
                type="password"
                value={loginPassword}
                onChange={e => setLoginPassword(e.target.value)}
                style={{
                  padding: "8px 12px",
                  marginTop: 7,
                  marginBottom: 12,
                  width: "100%",
                  border: "2px solid #e0e7ee",
                  borderRadius: "6px",
                  fontSize: "1.05rem",
                  background: "#f4f7fa"
                }}
                placeholder="Your password"
              />
            </label>
            <button style={{
              background: "linear-gradient(90deg,#2563eb 0,#3b82f6 100%)",
              color: "#fff",
              fontWeight: "600",
              fontSize: "1.08rem",
              border: "none",
              borderRadius: "8px",
              padding: "11px 20px",
              width: "100%",
              boxShadow: "0 2px 6px rgba(60,60,120,.09)",
              marginBottom: "11px",
              cursor: "pointer"
            }} type="submit">
              Login
            </button>
          </form>
          {loginError && <p style={{ color: "#ef4444", marginBottom: 16 }}>{loginError}</p>}
          <div style={{ display: "flex", gap: 11, justifyContent: "center" }}>
            <button style={{
              background: "#f4f7fa",
              color: "#2563eb",
              fontWeight: "500",
              fontSize: "1rem",
              textDecoration: "underline",
              border: "none",
              borderRadius: "6px",
              padding: "6px 14px",
              cursor: "pointer"
            }} type="button" onClick={() => setAuthPage("register")}>
              Register
            </button>
            <button style={{
              background: "#f4f7fa",
              color: "#2563eb",
              fontWeight: "500",
              fontSize: "1rem",
              textDecoration: "underline",
              border: "none",
              borderRadius: "6px",
              padding: "6px 14px",
              cursor: "pointer"
            }} type="button" onClick={() => setAuthPage("forgot")}>
              Forgot Password?
            </button>
          </div>
        </div>
      </div>
    );
  }

  // --- Register Page ---
  if (authPage === "register" && !currentUser) {
    return (
      <div className="auth-page">
        <div className="auth-card">
          <h2 style={{ color: "#2563eb", fontWeight: 700 }}>Register</h2>
          <form onSubmit={handleRegister}>
            <label>
              Username
              <input value={regUsername} onChange={e => setRegUsername(e.target.value)} />
            </label>
            <label>
              Password
              <input type="password" value={regPassword} onChange={e => setRegPassword(e.target.value)} />
            </label>
            <button type="submit">Register</button>
          </form>
          {regError && <p className="error-msg">{regError}</p>}
          {regSuccess && <p className="success-msg">{regSuccess}</p>}
          <button className="link-button" onClick={() => setAuthPage("login")}>Back to Login</button>
        </div>
      </div>
    );
  }

  // --- Forgot Password Page ---
  if (authPage === "forgot" && !currentUser) {
    return (
      <div className="auth-page">
        <div className="auth-card">
          <h2 style={{ color: "#2563eb", fontWeight: 700 }}>Reset Password</h2>
          <form onSubmit={handleForgot}>
            <label>
              Username
              <input value={forgotUsername} onChange={e => setForgotUsername(e.target.value)} />
            </label>
            <label>
              New Password
              <input type="password" value={forgotNewPassword} onChange={e => setForgotNewPassword(e.target.value)} />
            </label>
            <button type="submit">Reset Password</button>
          </form>
          {forgotError && <p className="error-msg">{forgotError}</p>}
          {forgotSuccess && <p className="success-msg">{forgotSuccess}</p>}
          <button className="link-button" onClick={() => setAuthPage("login")}>Back to Login</button>
        </div>
      </div>
    );
  }

  // --- Dashboard Page ---
  if (currentUser && authPage === "dashboard") {
    const filteredTasks = tasks.filter(FILTER_MAP[filter]);
    return (
      <div className="dashboard-container">
        <h2>Welcome, {currentUser}!</h2>
        <button onClick={handleLogout}>Logout</button>
        <h3>Task Dashboard</h3>
        <div className="filter-buttons">
          {FILTER_NAMES.map((name) => (
            <button
              key={name}
              className={filter === name ? "active" : ""}
              onClick={() => setFilter(name)}
            >
              {name}
            </button>
          ))}
        </div>
        <form onSubmit={addTask} style={{ margin: "12px 0" }}>
          <h4>Add New Task</h4>
          <label>
            Title
            <input
              value={newTask.title}
              onChange={e => setNewTask({ ...newTask, title: e.target.value })}
              required
            />
          </label>
          <label>
            Description
            <input
              value={newTask.description}
              onChange={e => setNewTask({ ...newTask, description: e.target.value })}
              required
            />
          </label>
          <label>
            Due Date
            <input
              type="date"
              value={newTask.dueDate}
              onChange={e => setNewTask({ ...newTask, dueDate: e.target.value })}
              required
            />
          </label>
          <label>
            Status
            <select
              value={newTask.status}
              onChange={e => setNewTask({ ...newTask, status: e.target.value })}
            >
              {STATUS_OPTIONS.map((option) => (
                <option key={option} value={option}>{option}</option>
              ))}
            </select>
          </label>
          <button type="submit">Add Task</button>
        </form>
        <ul>
          {filteredTasks.map(task =>
            <li key={task.id}>
              {editId === task.id ? (
                <form onSubmit={updateTask} className="edit-form">
                  <label>
                    Title
                    <input
                      value={editTask.title}
                      onChange={e => setEditTask({ ...editTask, title: e.target.value })}
                      required
                    />
                  </label>
                  <label>
                    Description
                    <input
                      value={editTask.description}
                      onChange={e => setEditTask({ ...editTask, description: e.target.value })}
                      required
                    />
                  </label>
                  <label>
                    Due Date
                    <input
                      type="date"
                      value={editTask.dueDate}
                      onChange={e => setEditTask({ ...editTask, dueDate: e.target.value })}
                      required
                    />
                  </label>
                  <label>
                    Status
                    <select
                      value={editTask.status}
                      onChange={e => setEditTask({ ...editTask, status: e.target.value })}
                    >
                      {STATUS_OPTIONS.map((option) => (
                        <option key={option} value={option}>{option}</option>
                      ))}
                    </select>
                  </label>
                  <button type="submit">Save</button>
                  <button type="button" onClick={() => setEditId(null)}>Cancel</button>
                </form>
              ) : (
                <>
                  <div className="task-details">
                    <strong>Title:</strong> {task.title}<br />
                    <strong>Description:</strong> {task.description}<br />
                    <strong>Due Date:</strong> {task.dueDate}<br />
                    <strong>Status:</strong> {task.status}
                  </div>
                  <div className="button-group">
                    <button onClick={() => startEdit(task)}>
                      Edit
                    </button>
                    <button onClick={() => deleteTask(task.id)}>
                      Delete
                    </button>
                    {task.status !== "complete" &&
                      <button onClick={() => markComplete(task.id)}>
                        Mark Complete
                      </button>
                    }
                    {task.status !== "pending" &&
                      <button onClick={() => markPending(task.id)}>
                        Mark Pending
                      </button>
                    }
                  </div>
                </>
              )}
            </li>
          )}
        </ul>
        {filteredTasks.length === 0 && <p>No tasks for "{filter}" filter.</p>}
      </div>
    );
  }

  // fallback for debugging
  return <div style={{textAlign: "center", marginTop: 80, fontSize: "2rem"}}>Hello World! React is working.</div>;
}
