import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";

export default function VerifyHandlerPage() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const token = searchParams.get("token");
    if (!token) {
      navigate("/verify-pending");
      return;
    }
    fetch(`http://localhost:8081/user/api/v1/verify-email?token=${token}`)
      .then(res => {
        navigate(res.ok ? "/verification-success" : "/verify-pending");
      });
  }, [searchParams, navigate]);

  return (
    <div className="min-h-screen flex items-center justify-center">
      <p>Verifying your account...</p>
    </div>
  );
}
