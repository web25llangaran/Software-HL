//=====================================
// inner-page-products.html KARGATZEAN:
//=====================================
function create_products_list() {
  //lokalStoragetik produktu zerrenda jaso:
  //JSON.parse_rekin String baten dauen txurro danai JSON extruktura ematen jako:
  const produktuak = JSON.parse(localStorage.getItem("products"));
  //Taulan JSONeko produktu guztiak kargatutzeko EGITURA:
  //Taulako egitura:
  // <table>
  //     <tbody>
  //         <tr>
  //             <td>
  //                  <img></img>
  //             </td>
  //             <td>
  //                 Lehenik eta behin Div bat sortu info guztia bertan sartzeko:
  //                 <div>
  //                   <p></p>
  //                   <ul>
  //                       <li></li>
  //                       <li></li>
  //                       <li></li>
  //                   </ul>
  //                  </div>
  //                  <div>
  //                      <a></a>
  //                  </div>
  //             </td>
  //         </tr>
  //     </tbody>
  // </table>
  //Taula eskuratu:
  let produk_taula = document.querySelector("#products_list");
  //Taulan marra txuri eta grisak agertzeko tbody bat sortu eta bertatik zintzilikatu errenkada guztiak:
  let produk_taula_tbody = document.createElement("tbody");
  produk_taula.append(produk_taula_tbody);
  //Produktu bakoitza taulan bistaratu:
  //===================================
  produktuak.forEach((element) => {
    //Produktuak bistaratzeko html oinarria sortu:
    //Errenkada berria sortu eta taulan txertatu:
    let errenkada_berria = document.createElement("tr");
    produk_taula_tbody.append(errenkada_berria);
    //Errenkadako bi celdak sortu, eta taulan txertatu:
    let irudi_celda = document.createElement("td");
    errenkada_berria.append(irudi_celda);
    irudi_celda.className = "irudia_celda";
    let info_celda = document.createElement("td");
    errenkada_berria.append(info_celda);
    //Image sortu eta taulan txertatu:
    let image = document.createElement("img");
    irudi_celda.append(image);
    //Div bat sortu, INFOrako elementu guztiak sortu eta divean sartu:
    let info_edukiontzia = document.createElement("div");
    info_celda.append(info_edukiontzia);
    info_edukiontzia.className = "info_edukiontzia";
    let id = document.createElement("p");
    let zerrenda = document.createElement("ul");
    let izena = document.createElement("li");
    let prezioa = document.createElement("li");
    let stock = document.createElement("li");
    info_edukiontzia.append(id);
    info_edukiontzia.append(zerrenda);
    zerrenda.append(izena);
    zerrenda.append(prezioa);
    zerrenda.append(stock);
    //Informazio gehiagorako BOTOIA sortu:
    //Div bat sortu botoia bertan kokatzeko CSSrako hobeto geratzeko:
    let botoi_edukiontzia = document.createElement("div");
    info_celda.append(botoi_edukiontzia);
    botoi_edukiontzia.className = "botoi_edukiontzia";
    //Botoia sortu:
    let info_gehigarria = document.createElement("a");
    botoi_edukiontzia.append(info_gehigarria);
    info_gehigarria.textContent = "Informazio gehigarria";
    info_gehigarria.className = "info_gehigarria botoia";
    info_gehigarria.id = element.id_pieza;
    //Onclick ebentuan definitu
    info_gehigarria.addEventListener("click", produktua_gorde_bistaratu);
    //Taulako errenkada bete. Infoa, taulan txertatu:
    image.src = `assets/img/products/${element.id_pieza}.jpg`;
    image.alt = element.izena;
    //Datuak:
    id.innerHTML = `<strong>ID Produktua: ${element.id_pieza}</strong>`;
    izena.innerHTML = `Izena: ${element.izena}`;
    prezioa.innerHTML = `Prezioa: ${element.prezioa}&nbsp;€`;
    stock.innerHTML = `Stocka: ${element.stock}`;
  });
}



//=====================================
// produktua_gorde_bistaratu FUNTZIOA:
//=====================================
//Produktuaren botoian CLICK egitean honen informazioa localStoragen gorde eta honen detaileak bistaratu:
function produktua_gorde_bistaratu(ebentua) {
  localStorage.setItem("produktu_zehatza", ebentua.target.id);
  // Birbideratu html berrira
  window.location.href = "product-details.html";
}



//=====================================
// product-details.html KARGATZEAN:
//=====================================
function fill_product_info() {
  //Eskuratu localStoragetik ikusi nahi den produktu_zehatzaren id-a:
  let produktua_id = localStorage.getItem("produktu_zehatza");
  //Eskuratu localStoragetik produktuen zerrenda:
  const produktuak = JSON.parse(localStorage.getItem("products"));
  // Bilatu bistaratu nahi den produktua produktuak zerrendan
  const produktua = produktuak.find((produk) => produk.id_pieza == produktua_id);
  if (produktua) {
    // Erakutsi datuak:
    //IDa
    const inf = document.querySelector("#info");
    const id = document.querySelector("#info h3");
    id.innerHTML = `ID Produktua: ${produktua.id_pieza}`;
    //Izena, prezioa, stocka eta deskribapena:
    //Hauek sartzeko eduki ontzia sortu
    let edukiontzia = document.createElement("div");
    inf.append(edukiontzia);
    edukiontzia.className = "info_edukiontzia";
    //Izena, prezioa, stocka eta deskribapena, sartzeko elementuak sortu
    let zerrenda = document.createElement("ul");
    let izena = document.createElement("li");
    let prezioa = document.createElement("li");
    let stock = document.createElement("li");
    edukiontzia.append(zerrenda);
    zerrenda.append(izena);
    zerrenda.append(prezioa);
    zerrenda.append(stock);
    //Deskribapenaren edukiontzia HTMLtik eskuratu:
    let deskribapena = document.querySelector("#description");
    //Izena, prezioa, stocka eta deskribapena, informazioa sartu
    izena.innerHTML = `<strong>Izena:</strong> ${produktua.izena}`;
    prezioa.innerHTML = `<strong>Prezioa:</strong> ${produktua.prezioa}&nbsp;€`;
    stock.innerHTML = `<strong>Stocka:</strong> ${produktua.stock}`;
    deskribapena.innerHTML = `<strong>Deskribapena:</strong> ${produktua.deskribapena}. <br> Pisua: ${produktua.pisua}gr.`;
    //Erosteko botoia sortu:
    let erosi_botoia = document.createElement("a");
    let erosi_div = document.querySelector("#DivErosketa");
    erosi_div.append(erosi_botoia);
    erosi_botoia.textContent = "Erosi";
    erosi_botoia.className = "erosi botoia";
    erosi_botoia.addEventListener("click", erosi);
  } else {
    alert("Sentitzen dugu, produktua ez dago eskuragarri!");
  }
}





//=====================================
// erosi FUNTZIOA:
//=====================================
function erosi() {
  let erosi = false;
  let zenbat_erosi = document.querySelector("#canti").value;
  //Erosi nahi den kopurua ezin da 0 baino txikiagoa izan
  //Kopurua zero baino handiagoa bada ezin da stock ean dagoena baino kopuru handiagoa erosi
  //Honetarako erosketa organ produktu hau iada badagoen eta bi eskaeren artean stock ez dela gainditzen frogatu behar da
  if (zenbat_erosi > 0) {
    //Eskuratu localStoragetik erosi nahi den produktuaren id-a
    let id_pieza = localStorage.getItem("produktu_zehatza");

    //Erosketa_orga jaso localStoragetik
    let erosketa_orga = JSON.parse(localStorage.getItem("erosketa_orga"));
    if (erosketa_orga == null) {
      erosi = true;
    } else {
      let existe = erosketa_orga.find((e) => e.id_pieza == id_pieza);
      if (existe != null) {
        console.log("gure: "+zenbat_erosi);
        console.log("sazkian: " +existe.items);
        console.log(zenbat_erosi + existe.items);
        if (existe.stock >= (parseInt(zenbat_erosi) +parseInt(existe.items))) {
          erosi = true;
        } else {
          alert("Erosketa orgako item kopurua gehi berriak, stock kopurua gainditzen du.");
        }
      } else {
        //Eskuratu localStoragetik produktuen zerrenda:
        const produktuak = JSON.parse(localStorage.getItem("products"));
        // Bilatu bistaratu nahi den produktua produktuak zerrendan
        const produktua = produktuak.find((produk) => produk.id_pieza == id_pieza);
        if (produktua.stock>= zenbat_erosi) {
          erosi = true;
        } else {
          alert("Erosi nahi den kopuruak, stock kopurua gainditzen du.");
        }
      }
    }
    if (erosi) {
      //Gorde localStoragen erosi nahi den zenbatekoa:
      //Erosi nahi den produktuaren id-a iada gordeta dago "produktu_zehatza" bezala localStoragen.
      //Beraz hau ez dugu berriz gordeko.
      localStorage.setItem("zenbat_erosi", zenbat_erosi);
      //payment_1 web orria kargatu:
      window.location.href = "payment_1.html";
    }
  }else alert("Sar ezazu Stock kopurua baino txikiagoa den zenbaki positibo bat!");
}



//=====================================
// payment_1.html KARGATZEAN:
//=====================================
function fill_payment_cards1() {
  //Eskuratu localStoragetik erosi nahi den produktuaren id-a
  let produktua_id = localStorage.getItem("produktu_zehatza");
  //Eskuratu localStoragetik erosi nahi den zenbatekoa:
  let zenbat_erosi = localStorage.getItem("zenbat_erosi");
  localStorage.setItem("zenbat_erosi", 0);
  //Erosketa orga irakurri localStoragetik. Ez badago ezer, sortu lehena oraingo datuekin:
  let erosketa_orga = JSON.parse(localStorage.getItem("erosketa_orga")); //|| [aukeratuak];
  if (erosketa_orga == null) {
    erosketa_orga = [];
  }
  if (zenbat_erosi > 0) {
    //Eskuratu localStoragetik produktuen zerrenda:
    const produktuak = JSON.parse(localStorage.getItem("products"));
    // produktua_id duen produktua aurkitu produktuak zerrendan:
    const produktua = produktuak.find((produk) => produk.id_pieza == produktua_id,);
    // Aukeratutako produktuaren objetua sortu, ondoren erosketa_organ atzitzeko:
    let aukeratua = {
      id_pieza: produktua.id_pieza,
      izena: produktua.izena,
      deskribapena: produktua.deskribapena,
      pisua: produktua.pisua,
      prezioa: produktua.prezioa,
      stock: produktua.stock,
      items: zenbat_erosi,
    };
    let existe = erosketa_orga.find((e) => e.id_pieza == aukeratua.id_pieza);
    if (existe) {
      existe.items = parseInt(existe.items) + parseInt(aukeratua.items);
    } else {
      erosketa_orga.push(aukeratua);
    }
    localStorage.setItem("erosketa_orga", JSON.stringify(erosketa_orga));
  }
  //Non bistaratu elementuaren infoa? Item hau eskuratu (#fill_cards), eta taula bat sortu bertan:
  // <table>
  //     <tr>
  //         <td><img></img></td>
  //         <td>
  //             <div>
  //                 <ul>
  //                     <li></li>
  //                     <li></li>
  //                     <li></li>
  //                     <li></li>
  //                     <li>kopurua:<a>items</a><a>-</a><a>+</a></li>
  //                     <li></li>
  //                 </ul>
  //             </div>
  //         </td>
  //     </tr>
  // </table>
  let inf = document.querySelector("#fill_cards");
  let aukera_taula = document.createElement("table");
  inf.append(aukera_taula);

  let itemsKont = 0;
  let prezioKont = 0;
  erosketa_orga.forEach((produkt) => {
    itemsKont = parseInt(itemsKont) + parseInt(produkt.items);
    let prezioa = produkt.prezioa * produkt.items;
    prezioKont = parseFloat(prezioKont) + parseFloat(prezioa);
    //Beharrezko html estruktura sortu: (Goian adierazitakoa)
    let aukera_errenkada = document.createElement("tr");
    aukera_taula.append(aukera_errenkada);
    //Errenkadako bi celdak sortu, eta taulan txertatu:
    let aukera_irudi_celda = document.createElement("td");
    aukera_errenkada.append(aukera_irudi_celda);
    aukera_irudi_celda.className = "aukera_irudia_celda";
    let aukera_info_celda = document.createElement("td");
    aukera_errenkada.append(aukera_info_celda);
    //Irudia bistaratu:
    let image = document.createElement("img");
    aukera_irudi_celda.append(image);
    image.className = "aukera_image";
    image.src = `assets/img/products/${produkt.id_pieza}.jpg`;
    image.alt = produkt.izena;
    //ID, izena, stocka, pisua eta deskribapena:
    //Hauek bistaratzeko eduki ontzia sortu:
    let info_edukiontzia = document.createElement("div");
    aukera_info_celda.append(info_edukiontzia);
    info_edukiontzia.className = "aukera_info_edukiontzia";
    //Izena, stocka, pisua eta deskribapena, sartzeko elementuak sortu:
    let id_a = document.createElement("p");
    let zerrenda = document.createElement("ul");
    let izena = document.createElement("li");
    let pisua = document.createElement("li");
    let deskribapena = document.createElement("li");
    let stock = document.createElement("li");
    //kopurua sartzeko elementuak sortu:
    let labelaItems = document.createElement("li");
    labelaItems.innerHTML = `<strong>Kopurua:</strong>`;
    let labelItemsDiv = document.createElement("div");
    labelItemsDiv.className = "itemsKontenedorea";
    //ITEMS eta bi botoi
    let items = document.createElement("a");
    items.id = `item_${produkt.id_pieza}`;
    let ken_botoia = document.createElement("a");
    ken_botoia.textContent = "<";
    ken_botoia.className = `gehiKenItemBotoia ${produkt.id_pieza}`;
    ken_botoia.id = "ken";
    ken_botoia.addEventListener("click", itemAldatuDa);
    let gehi_botoia = document.createElement("a");
    gehi_botoia.textContent = ">";
    gehi_botoia.className = `gehiKenItemBotoia ${produkt.id_pieza}`;
    gehi_botoia.id = "gehi";
    gehi_botoia.addEventListener("click", itemAldatuDa);
    let prezio_totala = document.createElement("li");
    prezio_totala.id = `prezio_totala_${produkt.id_pieza}`;
    info_edukiontzia.append(id_a);
    info_edukiontzia.append(zerrenda);
    zerrenda.append(izena);
    zerrenda.append(pisua);
    zerrenda.append(deskribapena);
    zerrenda.append(stock);
    zerrenda.append(labelaItems);
    labelaItems.append(labelItemsDiv);
    labelItemsDiv.append(ken_botoia);
    labelItemsDiv.append(items);
    labelItemsDiv.append(gehi_botoia);
    zerrenda.append(prezio_totala);
    //ID, izena, prezioa, stocka eta deskribapena, informazioa sartu
    id_a.innerHTML = `<strong>ID: ${produkt.id_pieza}</strong>`;
    izena.innerHTML = `<strong>Izena:</strong> ${produkt.izena}`;
    pisua.innerHTML = `<strong>Pisua:</strong> ${produkt.pisua}&nbsp;gr`;
    deskribapena.innerHTML = `<strong>Deskribapena:</strong> ${produkt.deskribapena}`;
    stock.innerHTML = `<strong>Stocka:</strong> ${produkt.stock}`;
    //items.value = produkt.items;
    items.innerHTML = `${produkt.items}`;
    prezio_totala.innerHTML = `<strong>Prezioa:</strong> ${prezioa}&nbsp€<br>(${produkt.prezioa}&nbsp;€/unitatea)`;
  });
  //payment_1.html web orritik kantitatea, prezioa eta guztizko prezioaren itemak eskuratu:
  let item = document.querySelector("#items");
  let prezioa = document.querySelector("#price");
  let prezioa_guztira = document.querySelector("#totalPrice");
  //Bistaratu
  item.innerHTML = `${itemsKont} items`;
  prezioa.innerHTML = `${prezioKont}&nbsp;€`;
  // let prezioa_totala = parseFloat(item.textContent) * parseFloat(prezioa.textContent);
  prezioa_guztira.innerHTML = `${prezioKont + 5}&nbsp;€`;
}




//=====================================
// itemAldatuDa FUNTZIOA:
//=====================================
function itemAldatuDa(ebentua) {
  //Ebentua ze produkturi dagokion jakiteko, clasea berreskuperatu,
  // hau array bat da eta 2.posizioan[0,1] produktuaren ida gorde da.
  let clasea = ebentua.target.className;
  let id_pieza = clasea.split(" ")[1];
  //Erosketa_orga jaso localStoragetik
  let erosketa_orga = JSON.parse(localStorage.getItem("erosketa_orga"));
  let existe = erosketa_orga.find((e) => e.id_pieza == id_pieza);
  //Ebentua ++ edo -- den identifikatzeko
  let eragiketa = ebentua.target.id;
  let item_aldaketa_dago = false;
  if (eragiketa == "ken") {
    if (existe.items > 1) {
      existe.items--;
      item_aldaketa_dago = true;
    } else {
      alert("Produktua erosketa orgatik kenduko da");
      let itemZenb = erosketa_orga.indexOf(existe);
      erosketa_orga.splice(itemZenb, 1);
      localStorage.setItem("erosketa_orga", JSON.stringify(erosketa_orga));
      //fill_payment_cards1();
      window.location.href = "payment_1.html";
    }
  }
  if (eragiketa == "gehi") {
    if (existe.items < existe.stock) {
      existe.items++;
      item_aldaketa_dago = true;
    } else alert("Ezin stock ean duguna baino gehiago erosi!");
  }
  if (item_aldaketa_dago) {
    existe.prezio_totala = existe.items * existe.prezioa;
    localStorage.setItem("erosketa_orga", JSON.stringify(erosketa_orga));
    //Bistaratu erosketa organ emandako aldaketak:
    let itemsKont = 0;
    let prezioKont = 0;
    erosketa_orga.forEach(element => {
      itemsKont = parseInt(itemsKont) + parseInt(element.items);
      let prezioa = element.prezioa * element.items;
      prezioKont = parseFloat(prezioKont) + parseFloat(prezioa);
    });
    //payment_1.html web orritik kantitatea, prezioa eta guztizko prezioaren itemak eskuratu:
    let item_guztira = document.querySelector("#items");
    let prezioa = document.querySelector("#price");
    let prezioa_guztira = document.querySelector("#totalPrice");
    let prezio_totala = document.querySelector(`#prezio_totala_${id_pieza}`);
    let produkt_item = document.querySelector(`#item_${id_pieza}`);
    console.log("itma: " + produkt_item);
    console.log("zenbat:" + existe.items);
    //Bistaratu
    item_guztira.innerHTML = `${itemsKont} items`;
    prezioa.innerHTML = `${prezioKont}&nbsp;€`;
    //let prezioa_totala = parseFloat(item.textContent) * parseFloat(prezioa.textContent);
    prezioa_guztira.innerHTML = `${prezioKont + 5}&nbsp;€`;
    //Produktuaren prezio_totala eta items kopuru berriak bistaratu
    prezio_totala.innerHTML = `<strong>Prezioa:</strong> ${existe.prezio_totala}&nbsp€ <br> (${existe.prezioa}&nbsp;€/unitatea)`;
    produkt_item.textContent = existe.items;
  }
}

//=====================================
// print.html KARGATZEAN:
//=====================================
function go2print() {
  //Erosketa_orga borratu localStoragetik
  localStorage.removeItem("erosketa_orga");
  window.location.href = "print.html";
}
function bete_print() {
  const erabiltzaile_edukiontzia = document.querySelector("#user");
  const erabiltzailea = JSON.parse(localStorage.getItem("username"));
  erabiltzaile_edukiontzia.textContent = erabiltzailea;
}
