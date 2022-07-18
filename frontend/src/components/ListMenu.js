
export const ListMenu = () => {
    const menu = [
        {
            year: '2022',
            round: 'qualification',
            dataset: ['a', 'b', 'c']
        },
        {
            year: '2022',
            round: 'finals',
            dataset: ['a', 'b', 'c', 'd', 'e']
        },
        {
            year: '2021',
            round: 'qualification',
            dataset: ['a', 'b', 'c', 'd', 'e', 'f']
        },
        {
            year: '2020',
            round: 'qualification',
            dataset: ['a', 'b', 'c', 'd', 'e']
        },
        {
            year: '2020',
            round: 'finals',
            dataset: ['a', 'b', 'c', 'd']
        }
    ]

    return <ul>{menu.map(item => <MenuItem year={item.year} round={item.round} />)}</ul>;
}

const MenuItem = ({ year, round }) => (
    <li>{year} - {round}</li>
);