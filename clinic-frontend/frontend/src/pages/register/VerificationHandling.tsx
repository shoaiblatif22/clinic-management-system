import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";

export default function VerifyHandlerPage() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const token = searchParams.get("token");
    if (!token) {
        throw new Error("No token provided");
    }
    fetch(`http://localhost:8080/api/v1/registration/verify?token=${token}`)
      .then(res => {
        if (res.ok) {
          navigate("/verification-success");
        } else {
          navigate("/verification-pending");
        }
      });
  }, [searchParams, navigate]);

  return (
    <div className="min-h-screen flex items-center justify-center">
      <p>Verifying your account...</p>
    </div>
  );
}