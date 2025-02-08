
interface Props {
    main_title: string;
    sub_title: string;
    onClick: () => void; // Add onClick prop
}

function HeroSection({ main_title, sub_title, onClick }: Props) {
    return (
        <div className="inline-flex flex-col items-center h-screen flex items-center justify-center  -mt-80 min-h-screen bg-center bg-cover bg-hero-pattern">
            <h1 className={"text-4xl font-bold mb-4 text-center text-white"}>{main_title}</h1>
            <p className={"text-xl text-center text-gray-300 mb-8"}>{sub_title}</p>
            <button
                onClick={onClick}
            >
                Get Started
            </button>
        </div>
    );
}

export default HeroSection;