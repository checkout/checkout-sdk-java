const lightCodeTheme = require('prism-react-renderer/themes/github');
const darkCodeTheme = require('prism-react-renderer/themes/dracula');

/** @type {import('@docusaurus/types').DocusaurusConfig} */
module.exports = {
  title: 'checkout-sdk-java',
  tagline: 'Checkout.com SDK for Java',
  url: 'https://checkout.github.io',
  baseUrl: "/checkout-sdk-java/",
  favicon: "img/favicon.png",
  organizationName: 'checkout',
  projectName: 'checkout-sdk-java',
  themeConfig: {
    prism: {
      additionalLanguages: ['java'],
    },
    googleAnalytics: {
      trackingID: "UA-165971486-1",
    },
    navbar: {
      title: "checkout-sdk-java",
      logo: {
        alt: "checkout-sdk-java",
        src: "img/logo.png",
      },
      items: [
        {
          to: "docs/introduction",
          activeBasePath: "docs",
          label: "Docs",
          position: "right",
        },
        {
          href: "https://github.com/checkout/checkout-sdk-java",
          label: "GitHub",
          position: "right",
        },
      ],
    },
    footer: {
      style: "dark",
      copyright: `© ${new Date().getFullYear()} Checkout.com`,
    }
  },
  presets: [
    [
      '@docusaurus/preset-classic',
      {
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      },
    ],
  ],
};
