import HeroSection from "../../components/HeroSection.tsx";

interface Props {
    main_title: string;
    sub_title: string;
    onClick: () => void;
}

function Landing({ main_title, sub_title, onClick }: Props) {
    return (
            <HeroSection main_title={main_title} sub_title={sub_title} onClick={onClick} />
    );
}

export default Landing;