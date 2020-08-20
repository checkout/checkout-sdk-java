module.exports = {
    title: 'checkout-sdk-java',
    tagline: 'Checkout.com SDK for Java',
    url: 'https://stephen-gilbert-cko.github.io',
    baseUrl: '/checkout-sdk-java/',
    favicon: 'img/favicon.png',
    organizationName: 'stephen-gilbert-cko', // Usually your GitHub org/user name.
    projectName: 'checkout-sdk-java', // Usually your repo name.
    themeConfig: {
        navbar: {
            title: 'checkout-sdk-java',
            logo: {
                alt: 'checkout-sdk-java',
                src: 'img/logo.png',
            },
            links: [
                {
                    to: 'getting_started',
                    activeBasePath: 'docs',
                    label: 'Docs',
                    position: 'right',
                },
                {
                    href: 'https://github.com/stephen-gilbert-cko/checkout-sdk-java',
                    label: 'GitHub',
                    position: 'right',
                },
            ],
        },
        footer: {
            style: 'dark',
            copyright: `© ${new Date().getFullYear()} Checkout.com    `,
        },
        googleAnalytics: {
            trackingID: 'UA-165971486-1',
        },
    },
    presets: [
        [
            '@docusaurus/preset-classic',
            {
                docs: {
                    sidebarPath: require.resolve('./sidebars.js'),
                    routeBasePath: '',
                    // editUrl: 'https://github.com/facebook/docusaurus/edit/master/website/'
                },
                theme: {
                    customCss: require.resolve('./src/css/custom.css'),
                },
            },
        ],
    ],
    plugins: [
        // Basic usage.
        require.resolve('@docusaurus/plugin-google-analytics'),
    ],
};
