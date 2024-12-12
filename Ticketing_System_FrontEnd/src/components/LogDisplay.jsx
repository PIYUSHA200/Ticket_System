import React from "react";
import "../assets/LogDisplay.css";

function LogDisplay({ logs }) {
  return (
    <div className="log-display">
      <h2>System Logs</h2>
      <div className="logs">
        {logs.map((log, index) => (
          <p key={index}>{log}</p>
        ))}
      </div>
    </div>
  );
}

export default LogDisplay;
