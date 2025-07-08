/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function removeAccents(str) {
    if (!str)
        return '';
    return str
            .normalize('NFD')
            .replace(/[\u0300-\u036f]/g, '')
            .replace(/đ/g, 'd')
            .replace(/Đ/g, 'D');
}

function setupAutocomplete(config) {
    const {visibleInput, hiddenInput, data, listContainer} = config;

    visibleInput.addEventListener('input', function () {
        const inputValue = this.value;
        const normalizedInputValue = removeAccents(inputValue.toLowerCase());

        listContainer.innerHTML = '';
        hiddenInput.value = inputValue;

        if (!inputValue) {
            return;
        }

        const suggestions = data.filter(item => {
            const itemName = typeof item === 'object' ? item.name : item;
            const normalizedItemName = removeAccents(itemName.toLowerCase());
            return normalizedItemName.includes(normalizedInputValue);
        });

        suggestions.forEach(item => {
            const suggestionDiv = document.createElement('div');
            const isObject = typeof item === 'object';

            suggestionDiv.textContent = isObject ? item.name : item;

            suggestionDiv.addEventListener('click', function () {
                visibleInput.value = this.textContent;
                hiddenInput.value = isObject ? item.id : this.textContent;
                listContainer.innerHTML = '';
            });

            listContainer.appendChild(suggestionDiv);
        });
    });
}

const colorInput = document.getElementById('colorInput');
const hiddenColorInput = document.getElementById('petColor');
const colorListContainer = document.getElementById('color-autocomplete-list');

const originInput = document.getElementById('originInput');
const hiddenOriginInput = document.getElementById('petOrigin');
const originListContainer = document.getElementById('origin-autocomplete-list');

setupAutocomplete({
    visibleInput: colorInput,
    hiddenInput: hiddenColorInput,
    data: colorData,
    listContainer: colorListContainer
});

setupAutocomplete({
    visibleInput: originInput,
    hiddenInput: hiddenOriginInput,
    data: originData,
    listContainer: originListContainer
});

document.addEventListener('click', function (e) {
    if (e.target !== colorInput && !colorListContainer.contains(e.target)) {
        colorListContainer.innerHTML = '';
    }

    if (e.target !== originInput && !originListContainer.contains(e.target)) {
        originListContainer.innerHTML = '';
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const priceDisplayInput = document.getElementById('priceDisplay');
    const priceHiddenInput = document.getElementById('petPrice');

    if (!priceDisplayInput || !priceHiddenInput) {
        return;
    }

    const formatNumber = (numStr) => {
        if (!numStr)
            return '';
        return numStr.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    };

    const getRawDigits = (str) => {
        return str.replace(/\D/g, '');
    }


    const initialPriceValue = priceHiddenInput.value;

    if (initialPriceValue) {
        const numericValue = parseFloat(initialPriceValue);

        const roundedValue = Math.round(numericValue);

        if (!isNaN(roundedValue)) {
            priceHiddenInput.value = roundedValue;

            priceDisplayInput.value = formatNumber(roundedValue.toString()); // "640.000"
        }
    }

    priceDisplayInput.addEventListener('input', function (e) {
        const rawValue = getRawDigits(e.target.value);

        priceHiddenInput.value = rawValue;

        const originalCursor = e.target.selectionStart;
        const originalLength = e.target.value.length;

        e.target.value = formatNumber(rawValue);

        const newLength = e.target.value.length;
        const cursorOffset = newLength - originalLength;

        // Ensure cursor doesn't jump unexpectedly
        if (cursorOffset > 0 && originalCursor > 0 && (originalCursor - 1) % 3 === 0) {
            e.target.setSelectionRange(originalCursor + cursorOffset, originalCursor + cursorOffset);
        } else {
            e.target.setSelectionRange(originalCursor, originalCursor);
        }
    });

});