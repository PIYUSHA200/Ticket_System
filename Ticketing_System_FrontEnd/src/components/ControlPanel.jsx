import React from "react";
import "../assets/ControlPanel.css";

function ControlPanel({ onStart, onStop, onVendorAdded, onCustomerPurchased, onReset }) {
  return (
    <div className="control-panel">
      <button onClick={onStart} className="start-btn">Start System</button>
      <button onClick={onStop} className="stop-btn">Stop System</button>
      <button onClick={onReset} className="reset-btn">Reset to Configuration</button>
    </div>
  );
}

export default ControlPanel;


